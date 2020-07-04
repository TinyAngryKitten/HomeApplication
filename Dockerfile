FROM gradle:6.5.1-jdk8 as builder

COPY . .

RUN gradle assembleDebug

FROM ahmedrshdy/firebase-cli

COPY --from=builder app/build/outputs/apk/debug/app-debug.apk .

# Code file to execute when the docker container starts up (`entrypoint.sh`)
ENTRYPOINT ["sh action.sh"]
