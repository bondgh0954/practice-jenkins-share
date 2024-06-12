#!/user/bin/env groovy

package com.example

class Docker implements Serializable{
    def script
    Docker(script){
        this.script=script
    }


    buildImage(String imageName){
        script.echo 'building application artifact into docker image'
        script.sh "docker build -t $imageName ."
    }

    dockerLogin(){
        script.echo 'logging in to the docker repository'
        script.withCredentials([script.usernamePassword(credentialsId:'dockerhub-credentials',usernameVariable:'USER',passwordVariable:'PASSWORD')]){
            script.sh "echo '${script.PASSWORD}' |docker login -u '${script.USER}' --password-stdin"
        }
    }

    pushImage(String imageName){
        script.echo 'pushing image into the artifact repository'
        script.sh "docker push $imageName"
    }

    buildJar(){
        script.echo 'building application artifact'
        script.sh 'mvn package'
    }


}