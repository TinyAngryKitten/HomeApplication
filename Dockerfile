FROM gradle:6.5.1-jdk8 as builder
COPY . .

RUN gradlew assembleRelease

FROM ahmedrshdy/firebase-cli

COPY --from=builder app/build/outputs/apk/release/app-release-unsigned.apk .

# Code file to execute when the docker container starts up (`entrypoint.sh`)
ENTRYPOINT ["sh action.sh"]
