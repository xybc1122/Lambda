pipeline {
  agent any
  stages {
    stage('fetch') {
      steps {
        git(url: 'git@192.168.3.222:root/lambda.git', branch: 'master', credentialsId: 'adbe6799-5267-4725-b76e-0c896492626f')
      }
    }
    stage('maven') {
      steps {
        sh 'mvn clean install'
      }
    }
    stage('maset') {
      parallel {
        stage('error') {
          steps {
            sh '''cp target/*.jar /home/tomcat/demo/
/home/tomcat/demo/demo.sh
'''
          }
        }
        stage('test') {
          steps {
            sh '''scp target/*.jar root@192.168.32.111:/home/tomcat/demo/
ssh -T tomcat@192.168.32.111 \'bash -s\' < /home/tomcat/demo/demo.sh'''
          }
        }
      }
    }
  }
}