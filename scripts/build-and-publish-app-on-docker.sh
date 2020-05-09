CMD_DOCKER_PS="docker ps | grep -i \"fco-manager\" | awk '{print \$1}'"
CMD_MVN_BUILD_APP="mvn clean compile install -Dmaven.test.skip=true"
CMD_REMOVE_CONTAINERS_ANTIGOS="docker rm \$(docker container ls -a --no-trunc | grep -i \"fcomanager\" | grep -i \"exited\" | awk '{print \$1}')"
DIRETORIO_PROJETO=`pwd`
APP_TAG_NAME="fco-manager:0.0.1"

CMD_DOCKER_RUN="docker run -d -p 8080:8080 $APP_TAG_NAME"


cd ../

echo "Inicia buildo do maven em:" 
pwd

#FAZ BUILD DA APLICAO
RETORNO_MAVEN_BUILD=`eval $CMD_MVN_BUILD_APP`

#echo $RETORNO_MAVEN_BUILD

if [[ $RETORNO_MAVEN_BUILD != *"BUILD SUCCESS"* ]]; then
    echo "Erro ao fazer build do maven"
    exit 1
fi


#FAZ BUILD NO DOCKER
NOME_CONTAINER_EXEC=`eval $CMD_DOCKER_PS`

echo "nome do cointainer que esta rodando=$NOME_CONTAINER_EXEC|" 

if [ ! -z "$NOME_CONTAINER_EXEC" ]; then
   echo "Parando container $NOME_CONTAINER_EXEC ... "
   echo `docker container stop $NOME_CONTAINER_EXEC`

   echo "Removendo containers antigos da aplicacao..."
   
   RETORNO_REMOVE_CONTAINERS_ANTIGOS=`eval $CMD_REMOVE_CONTAINERS_ANTIGOS`


fi


RETORNO_DOCKER_BUILD=`docker build -t $APP_TAG_NAME .`

#echo "$RETORNO_DOCKER_BUILD"


if [[ $RETORNO_DOCKER_BUILD == *"Successfully tagged"* ]]; then
  echo "Build feito com sucesso"

  echo "Iniciando execucao de container..."

  RETORNO_DOCKER_RUN=`eval $CMD_DOCKER_RUN`

  if [[ $RETORNO_DOCKER_RUN == *"failed"* ]]; then

      echo "Falha na execucao do container"
      
      exit 1     
  fi

  NOME_CONTAINER_EXEC=`eval $CMD_DOCKER_PS`

  if [ ! -z "$NOME_CONTAINER_EXEC" ]; then
     echo "Container esta funcionando ... "
  else
     echo "Nao foi possivel iniciar o container"	  
  fi

fi


