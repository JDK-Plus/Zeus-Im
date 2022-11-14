package plus.jdk.zeus.passport.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import plus.jdk.zeus.common.grpc.auth.client.ZeusGrpcSubClientFactory;
import plus.jdk.zeus.common.grpc.protoc.GreeterGrpc.*;
import plus.jdk.zeus.common.grpc.protoc.HelloReply;
import plus.jdk.zeus.common.grpc.protoc.HelloRequest;

import javax.annotation.Resource;


@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
class GreeterImplServiceTest {

    @Resource
    private ZeusGrpcSubClientFactory zeusGrpcSubClientFactory;

    @Test
    void testGreeterSayHello() {
        ManagedChannelBuilder<?> channelBuilder = ManagedChannelBuilder.forAddress("127.0.0.1", 10500)
                .usePlaintext();
        GreeterBlockingStub greeterBlockingStub = zeusGrpcSubClientFactory.createStub(GreeterBlockingStub.class, channelBuilder, "zeus");
        HelloRequest helloRequest = HelloRequest.newBuilder().setName("zeus").build();
        HelloReply helloReply = greeterBlockingStub.sayHello(helloRequest);
        log.info("{}", helloReply);
        helloReply = greeterBlockingStub.sayHelloAgain(helloRequest);
        log.info("{}", helloReply);
    }
}