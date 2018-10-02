# BigCloneWe: Evaluation

Evaluation stage works as follows:

1.Go to https://hub.docker.com/r/khaledkucse/bigclonewe/tags/ to see evaluation's docker image
2. Install docke engine in your system
3. Open Command Prompt or Terminal and write "docker pull khaledkucse/bigclonewe:evaluaion0.1". It will pull the docker image of evaluation stage. TO check whether image is successfully pulled, write "docker images" after pulling
4. Write "docker run --r -it -p 8080:8080 khaledkucse/bigclonewe:evaluation0.1" . The command will ransfer the control into docker image.
5. inside docker write "java -jar bigcloneeval.evaluation.jar" to run the springboot application.
6. GO to your web browser and go to http://localhost:8080/cloneranking/home and you will get the interface you are looking for.

For any query please an issue.

Thanks.

