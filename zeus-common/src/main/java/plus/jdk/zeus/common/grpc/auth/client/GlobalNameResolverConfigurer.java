package plus.jdk.zeus.common.grpc.auth.client;

import io.grpc.EquivalentAddressGroup;
import plus.jdk.grpc.client.INameResolverConfigurer;
import plus.jdk.grpc.model.GrpcNameResolverModel;
import plus.jdk.zeus.common.properties.ZeusGrpcProperties;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class GlobalNameResolverConfigurer  implements INameResolverConfigurer {

    private final ZeusGrpcProperties properties;

    public GlobalNameResolverConfigurer(ZeusGrpcProperties properties) {
        this.properties = properties;
    }


    @Override
    public List<EquivalentAddressGroup> configurationName(URI targetUri) {
        return new ArrayList<>();
    }

    @Override
    public void configureNameResolvers(List<GrpcNameResolverModel> resolverModels) {

    }
}
