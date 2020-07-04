export CLI_TOKEN=$1
export APPID=$2
export GROUPS=$3

firebase appdistribution:distribute app/build/outputs/apk/debug/app-debug.apk --app $APPID --token $CLI_TOKEN --groups $GROUPS
