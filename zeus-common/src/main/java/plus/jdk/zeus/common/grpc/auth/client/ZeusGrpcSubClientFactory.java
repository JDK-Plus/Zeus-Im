package plus.jdk.zeus.common.grpc.auth.client;

import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.AbstractStub;
import plus.jdk.grpc.client.GrpcSubClientFactory;
import plus.jdk.zeus.common.RSACipherService;

public class ZeusGrpcSubClientFactory {

    private final GrpcSubClientFactory grpcSubClientFactory;

    private final RSACipherService rsaCipherService;

    public ZeusGrpcSubClientFactory(GrpcSubClientFactory grpcSubClientFactory, RSACipherService rsaCipherService) {
        this.grpcSubClientFactory = grpcSubClientFactory;
        this.rsaCipherService = rsaCipherService;
    }

    public <T extends AbstractStub<T>>
    T createStub(final Class<T> stubClass, ManagedChannelBuilder<?> channelBuilder, String appKey) {
        channelBuilder.intercept(new ZeusGrpcClientRSAInterceptor(appKey, rsaCipherService));
        return grpcSubClientFactory.createStub(stubClass, channelBuilder);
    }
}
