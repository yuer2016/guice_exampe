package com.yicheng.standard;


import com.yicheng.standard.antlr4.JsonBaseListener;
import com.yicheng.standard.antlr4.JsonLexer;
import com.yicheng.standard.antlr4.JsonParser;
import lombok.SneakyThrows;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;


public class Json2Xml extends JsonBaseListener {
    public static class XMLEmitter extends JsonBaseListener {
        ParseTreeProperty<String> xml = new ParseTreeProperty<>();

        String getXML(ParseTree ctx) {
            return xml.get(ctx);
        }

        void setXML(ParseTree ctx, String s) {
            xml.put(ctx, s);
        }

        public void exitJson(JsonParser.JsonContext ctx) {
            setXML(ctx, getXML(ctx.getChild(0)));
        }

        public void exitAnObject(JsonParser.AnObjectContext ctx) {
            StringBuilder builder = new StringBuilder();
            builder.append("\n");
            ctx.pair().forEach(pctx -> builder.append(getXML(pctx)));
            setXML(ctx, builder.toString());
        }

        public void exitAtom(JsonParser.AtomContext ctx) {
            setXML(ctx, ctx.getText());
        }


        public void exitString(JsonParser.StringContext ctx) {
            setXML(ctx, stripQuotes(ctx.getText()));
        }


        public void exitObjectValue(JsonParser.ObjectValueContext ctx) {
            setXML(ctx, getXML(ctx.obj()));
        }

        public void exitPair(JsonParser.PairContext ctx) {
            String tag = stripQuotes(ctx.STRING().getText());
            JsonParser.ValueContext vctx = ctx.value();
            String str = String.format("<%s>%s</%s>\n", tag, getXML(vctx), tag);
            setXML(ctx, str);
        }

        public void exitEmptyObject(JsonParser.EmptyObjectContext ctx) {
            setXML(ctx, "");
        }

        public void exitArrayOfValues(JsonParser.ArrayOfValuesContext ctx) {
            StringBuilder builder = new StringBuilder();
            builder.append("\n");
            ctx.value().forEach(v -> {
                builder.append("<element>");
                builder.append(getXML(v));
                builder.append("</element>");
                builder.append("\n");
            });
            setXML(ctx, builder.toString());
        }

        public void exitEmptyArray(JsonParser.EmptyArrayContext ctx) {
            setXML(ctx, "");
        }

        private String stripQuotes(String s) {
            if (s == null || s.charAt(0) != '"') return s;
            return s.substring(1, s.length() - 1);
        }
    }

    @SneakyThrows
    public static void main(String[] args) {
        InputStream is = new FileInputStream(new File("/Users/yuer/Documents/IdeaProjects/guice_exampe/src/main/java/com/yicheng/standard/hello.json"));
        ANTLRInputStream input = new ANTLRInputStream(is);
        //新建词法分析器，处理输入的CharSteam
        JsonLexer lexer = new JsonLexer(input);
        //新建词法分析器的缓冲区，用于存储词法分析器生成的词法符号
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        //新建一个语法分析器，处理词法符号缓冲区内容
        JsonParser parser = new JsonParser(tokens);
        //parser.setBuildParseTree(true); 指定在解析过程中解析器是否应构造解析树 默认为 true
        ParseTree tree = parser.json();
        // System.out.println(tree.toStringTree(parser));

        ParseTreeWalker walker = new ParseTreeWalker();
        XMLEmitter converter = new XMLEmitter();
        //使用监听器初始化对语法分析树的遍历
        walker.walk(converter, tree);
        System.out.println(converter.getXML(tree));
    }

}
