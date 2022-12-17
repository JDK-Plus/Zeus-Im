package plus.jdk.zeus.passport.grpc;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import plus.jdk.grpc.annotation.GrpcClient;
import plus.jdk.zeus.common.grpc.protoc.GreeterGrpc.*;
import plus.jdk.zeus.common.grpc.protoc.HelloReply;
import plus.jdk.zeus.common.grpc.protoc.HelloRequest;


@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
class GreeterImplServiceTest {

    @GrpcClient
    private GreeterBlockingStub greeterBlockingStub;

    @Test
    void testGreeterSayHello() {
        HelloRequest helloRequest = HelloRequest.newBuilder().setName("zeus").build();
        HelloReply helloReply = greeterBlockingStub.sayHello(helloRequest);
        log.info("{}", helloReply);
        helloReply = greeterBlockingStub.sayHelloAgain(helloRequest);
        log.info("{}", helloReply);
    }
}