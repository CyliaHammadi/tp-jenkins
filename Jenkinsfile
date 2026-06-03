pipeline {

    agent any

    tools {
        maven 'Maven3'
        jdk   'JDK17'
    }

    parameters {
        choice(
            name:        'ENVIRONMENT',
            choices:     ['dev', 'staging', 'prod'],
            description: 'Environnement cible'
        )
        booleanParam(
            name:         'SKIP_TESTS',
            defaultValue: false,
            description:  'Passer les tests (urgence uniquement)'
        )
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
                echo "Commit : ${env.GIT_COMMIT}"
                echo "Environnement : ${params.ENVIRONMENT}"
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean compile -B'
            }
        }

        stage('Tests unitaires') {
            when {
                not { expression { return params.SKIP_TESTS } }
            }
            steps {
                sh 'mvn test -B'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
                failure {
                    echo 'Tests unitaires en echec'
                }
            }
        }

        stage('Tests integration') {
            when {
                not { expression { return params.SKIP_TESTS } }
            }
            steps {
                sh 'mvn verify -Dsurefire.skip=true -B'
            }
            post {
                always {
                    junit '**/target/failsafe-reports/*.xml'
                }
            }
        }

        stage('Couverture JaCoCo') {
            steps {
                sh 'mvn jacoco:report -B'
            }
            post {
                always {
                    jacoco(
                        execPattern:         '**/target/jacoco.exec',
                        classPattern:        '**/target/classes',
                        sourcePattern:       '**/src/main/java',
                        minimumLineCoverage: '70'
                    )
                }
            }
        }

        stage('Qualite') {
            steps {
                sh 'mvn checkstyle:checkstyle pmd:pmd pmd:cpd spotbugs:spotbugs -B'
            }
            post {
                always {
                    recordIssues(
                        enabledForFailure: true,
                        tools: [
                            checkStyle(pattern: '**/checkstyle-result.xml'),
                            pmdParser(pattern:  '**/pmd.xml'),
                            cpd(pattern:        '**/cpd.xml'),
                            spotBugs(pattern:   '**/spotbugsXml.xml')
                        ],
                        qualityGates: [[
                            threshold: 10,
                            type: 'TOTAL',
                            unstable: true
                        ]]
                    )
                }
            }
        }

        stage('Archive') {
            steps {
                archiveArtifacts(
                    artifacts:         '**/target/*.jar',
                    fingerprint:       true,
                    allowEmptyArchive: false
                )
            }
        }

        stage('Validation PROD') {
            when {
                expression { return params.ENVIRONMENT == 'prod' }
            }
            steps {
                timeout(time: 1, unit: 'HOURS') {
                    input(
                        message:   'Deployer en PRODUCTION ?',
                        ok:        'Oui, deployer',
                        submitter: 'admin'
                    )
                }
            }
        }

    }

    post {
        success {
            echo "Build reussi - ${env.JOB_NAME} #${env.BUILD_NUMBER}"
        }
        failure {
            emailext(
                subject: "ECHEC : ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body:    "Build en echec. Voir : ${env.BUILD_URL}",
                to:      'equipe-dev@monentreprise.fr'
            )
        }
        fixed {
            emailext(
                subject: "CORRIGE : ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body:    "Build de nouveau stable : ${env.BUILD_URL}",
                to:      'equipe-dev@monentreprise.fr'
            )
        }
    }

}