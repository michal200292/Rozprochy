package sr.proto;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.62.2)",
    comments = "Source: match.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class PublisherGrpc {

  private PublisherGrpc() {}

  public static final java.lang.String SERVICE_NAME = "Publisher";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<sr.proto.Nothing,
      sr.proto.Team> getGetAvailableTeamsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetAvailableTeams",
      requestType = sr.proto.Nothing.class,
      responseType = sr.proto.Team.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<sr.proto.Nothing,
      sr.proto.Team> getGetAvailableTeamsMethod() {
    io.grpc.MethodDescriptor<sr.proto.Nothing, sr.proto.Team> getGetAvailableTeamsMethod;
    if ((getGetAvailableTeamsMethod = PublisherGrpc.getGetAvailableTeamsMethod) == null) {
      synchronized (PublisherGrpc.class) {
        if ((getGetAvailableTeamsMethod = PublisherGrpc.getGetAvailableTeamsMethod) == null) {
          PublisherGrpc.getGetAvailableTeamsMethod = getGetAvailableTeamsMethod =
              io.grpc.MethodDescriptor.<sr.proto.Nothing, sr.proto.Team>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetAvailableTeams"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  sr.proto.Nothing.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  sr.proto.Team.getDefaultInstance()))
              .setSchemaDescriptor(new PublisherMethodDescriptorSupplier("GetAvailableTeams"))
              .build();
        }
      }
    }
    return getGetAvailableTeamsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<sr.proto.Nothing,
      sr.proto.Stats> getStreamEventsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "StreamEvents",
      requestType = sr.proto.Nothing.class,
      responseType = sr.proto.Stats.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<sr.proto.Nothing,
      sr.proto.Stats> getStreamEventsMethod() {
    io.grpc.MethodDescriptor<sr.proto.Nothing, sr.proto.Stats> getStreamEventsMethod;
    if ((getStreamEventsMethod = PublisherGrpc.getStreamEventsMethod) == null) {
      synchronized (PublisherGrpc.class) {
        if ((getStreamEventsMethod = PublisherGrpc.getStreamEventsMethod) == null) {
          PublisherGrpc.getStreamEventsMethod = getStreamEventsMethod =
              io.grpc.MethodDescriptor.<sr.proto.Nothing, sr.proto.Stats>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "StreamEvents"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  sr.proto.Nothing.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  sr.proto.Stats.getDefaultInstance()))
              .setSchemaDescriptor(new PublisherMethodDescriptorSupplier("StreamEvents"))
              .build();
        }
      }
    }
    return getStreamEventsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<sr.proto.Subscription,
      sr.proto.SubscriptionReply> getSubscribeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Subscribe",
      requestType = sr.proto.Subscription.class,
      responseType = sr.proto.SubscriptionReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
  public static io.grpc.MethodDescriptor<sr.proto.Subscription,
      sr.proto.SubscriptionReply> getSubscribeMethod() {
    io.grpc.MethodDescriptor<sr.proto.Subscription, sr.proto.SubscriptionReply> getSubscribeMethod;
    if ((getSubscribeMethod = PublisherGrpc.getSubscribeMethod) == null) {
      synchronized (PublisherGrpc.class) {
        if ((getSubscribeMethod = PublisherGrpc.getSubscribeMethod) == null) {
          PublisherGrpc.getSubscribeMethod = getSubscribeMethod =
              io.grpc.MethodDescriptor.<sr.proto.Subscription, sr.proto.SubscriptionReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Subscribe"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  sr.proto.Subscription.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  sr.proto.SubscriptionReply.getDefaultInstance()))
              .setSchemaDescriptor(new PublisherMethodDescriptorSupplier("Subscribe"))
              .build();
        }
      }
    }
    return getSubscribeMethod;
  }

  private static volatile io.grpc.MethodDescriptor<sr.proto.Nothing,
      sr.proto.Nothing> getPingMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Ping",
      requestType = sr.proto.Nothing.class,
      responseType = sr.proto.Nothing.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<sr.proto.Nothing,
      sr.proto.Nothing> getPingMethod() {
    io.grpc.MethodDescriptor<sr.proto.Nothing, sr.proto.Nothing> getPingMethod;
    if ((getPingMethod = PublisherGrpc.getPingMethod) == null) {
      synchronized (PublisherGrpc.class) {
        if ((getPingMethod = PublisherGrpc.getPingMethod) == null) {
          PublisherGrpc.getPingMethod = getPingMethod =
              io.grpc.MethodDescriptor.<sr.proto.Nothing, sr.proto.Nothing>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Ping"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  sr.proto.Nothing.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  sr.proto.Nothing.getDefaultInstance()))
              .setSchemaDescriptor(new PublisherMethodDescriptorSupplier("Ping"))
              .build();
        }
      }
    }
    return getPingMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static PublisherStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PublisherStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PublisherStub>() {
        @java.lang.Override
        public PublisherStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PublisherStub(channel, callOptions);
        }
      };
    return PublisherStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static PublisherBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PublisherBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PublisherBlockingStub>() {
        @java.lang.Override
        public PublisherBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PublisherBlockingStub(channel, callOptions);
        }
      };
    return PublisherBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static PublisherFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PublisherFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PublisherFutureStub>() {
        @java.lang.Override
        public PublisherFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PublisherFutureStub(channel, callOptions);
        }
      };
    return PublisherFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void getAvailableTeams(sr.proto.Nothing request,
        io.grpc.stub.StreamObserver<sr.proto.Team> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetAvailableTeamsMethod(), responseObserver);
    }

    /**
     */
    default void streamEvents(sr.proto.Nothing request,
        io.grpc.stub.StreamObserver<sr.proto.Stats> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getStreamEventsMethod(), responseObserver);
    }

    /**
     */
    default io.grpc.stub.StreamObserver<sr.proto.Subscription> subscribe(
        io.grpc.stub.StreamObserver<sr.proto.SubscriptionReply> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getSubscribeMethod(), responseObserver);
    }

    /**
     */
    default void ping(sr.proto.Nothing request,
        io.grpc.stub.StreamObserver<sr.proto.Nothing> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getPingMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service Publisher.
   */
  public static abstract class PublisherImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return PublisherGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service Publisher.
   */
  public static final class PublisherStub
      extends io.grpc.stub.AbstractAsyncStub<PublisherStub> {
    private PublisherStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PublisherStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PublisherStub(channel, callOptions);
    }

    /**
     */
    public void getAvailableTeams(sr.proto.Nothing request,
        io.grpc.stub.StreamObserver<sr.proto.Team> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getGetAvailableTeamsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void streamEvents(sr.proto.Nothing request,
        io.grpc.stub.StreamObserver<sr.proto.Stats> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getStreamEventsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<sr.proto.Subscription> subscribe(
        io.grpc.stub.StreamObserver<sr.proto.SubscriptionReply> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncClientStreamingCall(
          getChannel().newCall(getSubscribeMethod(), getCallOptions()), responseObserver);
    }

    /**
     */
    public void ping(sr.proto.Nothing request,
        io.grpc.stub.StreamObserver<sr.proto.Nothing> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getPingMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service Publisher.
   */
  public static final class PublisherBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<PublisherBlockingStub> {
    private PublisherBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PublisherBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PublisherBlockingStub(channel, callOptions);
    }

    /**
     */
    public java.util.Iterator<sr.proto.Team> getAvailableTeams(
        sr.proto.Nothing request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getGetAvailableTeamsMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<sr.proto.Stats> streamEvents(
        sr.proto.Nothing request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getStreamEventsMethod(), getCallOptions(), request);
    }

    /**
     */
    public sr.proto.Nothing ping(sr.proto.Nothing request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getPingMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service Publisher.
   */
  public static final class PublisherFutureStub
      extends io.grpc.stub.AbstractFutureStub<PublisherFutureStub> {
    private PublisherFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PublisherFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PublisherFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<sr.proto.Nothing> ping(
        sr.proto.Nothing request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getPingMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_AVAILABLE_TEAMS = 0;
  private static final int METHODID_STREAM_EVENTS = 1;
  private static final int METHODID_PING = 2;
  private static final int METHODID_SUBSCRIBE = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_AVAILABLE_TEAMS:
          serviceImpl.getAvailableTeams((sr.proto.Nothing) request,
              (io.grpc.stub.StreamObserver<sr.proto.Team>) responseObserver);
          break;
        case METHODID_STREAM_EVENTS:
          serviceImpl.streamEvents((sr.proto.Nothing) request,
              (io.grpc.stub.StreamObserver<sr.proto.Stats>) responseObserver);
          break;
        case METHODID_PING:
          serviceImpl.ping((sr.proto.Nothing) request,
              (io.grpc.stub.StreamObserver<sr.proto.Nothing>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SUBSCRIBE:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.subscribe(
              (io.grpc.stub.StreamObserver<sr.proto.SubscriptionReply>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getGetAvailableTeamsMethod(),
          io.grpc.stub.ServerCalls.asyncServerStreamingCall(
            new MethodHandlers<
              sr.proto.Nothing,
              sr.proto.Team>(
                service, METHODID_GET_AVAILABLE_TEAMS)))
        .addMethod(
          getStreamEventsMethod(),
          io.grpc.stub.ServerCalls.asyncServerStreamingCall(
            new MethodHandlers<
              sr.proto.Nothing,
              sr.proto.Stats>(
                service, METHODID_STREAM_EVENTS)))
        .addMethod(
          getSubscribeMethod(),
          io.grpc.stub.ServerCalls.asyncClientStreamingCall(
            new MethodHandlers<
              sr.proto.Subscription,
              sr.proto.SubscriptionReply>(
                service, METHODID_SUBSCRIBE)))
        .addMethod(
          getPingMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              sr.proto.Nothing,
              sr.proto.Nothing>(
                service, METHODID_PING)))
        .build();
  }

  private static abstract class PublisherBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    PublisherBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return sr.proto.Match.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Publisher");
    }
  }

  private static final class PublisherFileDescriptorSupplier
      extends PublisherBaseDescriptorSupplier {
    PublisherFileDescriptorSupplier() {}
  }

  private static final class PublisherMethodDescriptorSupplier
      extends PublisherBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    PublisherMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (PublisherGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new PublisherFileDescriptorSupplier())
              .addMethod(getGetAvailableTeamsMethod())
              .addMethod(getStreamEventsMethod())
              .addMethod(getSubscribeMethod())
              .addMethod(getPingMethod())
              .build();
        }
      }
    }
    return result;
  }
}
