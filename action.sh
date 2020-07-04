export CLI_TOKEN=$1
export APPID=$2
export GROUPS=$3

firebase appdistribution:distribute app/build/outputs/apk/release/app-release-unsigned.apk --app $APPID --token $CLI_TOKEN --groups $GROUPS
