pipeline {
    agent any

    tools {
        jdk 'JDK17'
        maven 'Maven3'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/CyliaHammadi/tp-jenkins.git'
            }
        }

        stage('Compile') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Tests unitaires') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit '**/surefire-reports/*.xml'
                }
            }
        }

        stage('Tests integration') {
            steps {
                sh 'mvn verify -DskipUnitTests'
            }
            post {
                always {
                    junit '**/failsafe-reports/*.xml'
                }
            }
        }

        stage('Qualite du code') {
            steps {
                sh 'mvn checkstyle:checkstyle pmd:pmd pmd:cpd spotbugs:spotbugs'
            }
            post {
                always {
                    recordIssues tools: [
                        checkStyle(pattern: '**/checkstyle-result.xml'),
                        pmdParser(pattern: '**/pmd.xml'),
                        cpd(pattern: '**/cpd.xml'),
                        spotBugs(pattern: '**/spotbugsXml.xml')
                    ]
                }
            }
        }
    }

    post {
        always {
            echo "Pipeline termine : ${currentBuild.result}"
        }
    }
}