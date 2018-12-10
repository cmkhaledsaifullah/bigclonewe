# BigCloneWe: Evaluation

The website devloped using the source code of this repository can be found at hhtps://bigclonewe.usask.ca

If you want to run the repository remotly then you have three ways to do that:


Way 1:
Download the Source code by cloning the repository and run the MainApplication.java file.

In the browser go to http://localhost:9057/


Way 2:
Download the jar file "bigcloneeval.evaluation.war" from the repository and then run the war file using following command:

"java -jar bigcloneeval.evaluation.war"


Way 3:
Evaluation stage works as follows:

1. Go to https://hub.docker.com/r/khaledkucse/bigclonewe/tags/ to see evaluation's docker image
2. Install docker engine in your system.
3. Open Command Prompt or Terminal and write "docker pull khaledkucse/bigclonewe:evaluaion0.1". It will pull the docker image of evaluation stage. To check whether image is successfully pulled, write "docker images" after pulling.
4. Write "docker run --rm -it -p 8080:8080 khaledkucse/bigclonewe:evaluation0.1" . The command will transfer the control into docker image.
5. Now write "java -jar bigcloneeval.evaluation.jar" to run the springboot application.
6. Go to your web browser and navigate http://localhost:8080/cloneranking/home and you will get the interface you are looking for.

For any query please create an issue.

Thanks.

