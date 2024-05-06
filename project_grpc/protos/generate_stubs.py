from grpc_tools import protoc


if __name__ == "__main__":
    protoc.main(
        (
            "--proto_path=.",
            "--python_out=../stub/python",
            "--pyi_out=../stub/python",
            "--grpc_python_out=../stub/python",
            "match.proto",
        )
    )