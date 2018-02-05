package com.yicheng.protos;


import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;


import java.util.concurrent.TimeUnit;


@Slf4j
public class RpcClient {

    private final ManagedChannel channel;

    /**
     * Construct client connecting to HelloWorld server at {@code host:port}.
     */
    public RpcClient(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext(true)
                .build());
    }

    /**
     * Construct client for accessing RouteGuide server using the existing channel.
     */
    RpcClient(ManagedChannel channel) {
        this.channel = channel;
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(15, TimeUnit.MINUTES);
    }

/*    public final void push() {
        Jtt.BaseRequest baseRequest = Jtt.BaseRequest.newBuilder()
                .setDeviceId("15929554571").setDeviceType(11).setServiceId("jtt").build();
        Iterator<Jtt.Respone> responeIterator = blockingStub.subVehicleLocation(baseRequest);
        while (responeIterator.hasNext()) {
            System.out.println("接受到数据:" + responeIterator.next().getLocation());
        }
    }*/


    /**
     * Say hello to server.
     */
    private final void greet(Jtt.BaseRequest baseRequest) {

            try {
                JttRequestGrpc.JttRequestStub stub = JttRequestGrpc.newStub(channel);
                stub.subVehicleLocation(baseRequest, new StreamObserver<Jtt.Respone>() {
                    @Override
                    public void onNext(Jtt.Respone respone) {
                        log.info("接收到返回数据:{}", respone.getLocation());
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        log.error(throwable.getMessage());
                    }

                    @Override
                    public void onCompleted() {
                    }
                });
            } catch (StatusRuntimeException e) {
                log.info("RPC failed: {}", e.getStatus());
            }

    }

    public static void main(String[] args) throws Exception {
        final RpcClient client = new RpcClient("localhost", 8888);
        try {
            Jtt.BaseRequest baseRequest = Jtt.BaseRequest.newBuilder()
                    .setDeviceId("15929554571").setDeviceType(11).setServiceId("jtt").build();
            client.greet(baseRequest);
            Thread.sleep(1000000);
        } finally {
            client.shutdown();
        }
    }
}
