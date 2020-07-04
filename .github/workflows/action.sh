export CLI_TOKEN=$1
export APP=$2
export GROUPS=$3

firebase appdistribution:distribute --app $APP --token $CLI_TOKEN --groups $GROUPS
