pipeline {
    agent any

    tools {
        maven 'Maven-3.9' // Assure-toi que Maven est configuré dans Jenkins sous ce nom
    }

    stages {

        stage('Checkout') {
            steps {
                echo 'Récupération du code source...'
                checkout scm
            }
        }

        stage('Build') {
            steps {
                echo 'Compilation du projet...'
                sh 'mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                echo 'Exécution des tests...'
                sh 'mvn test'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                echo 'Analyse SonarQube...'
                withSonarQubeEnv('SonarQube') { // Nom défini dans Jenkins Configuration
                    sh '''
                    mvn clean verify sonar:sonar \
                        -Dsonar.projectKey=calculatrice \
                        -Dsonar.host.url=http://localhost:9000 \
                        -Dsonar.login=sqp_dc414376baf22b4c74012cd50f0e223d9ef3b9b0
                    '''
                }
            }
        }

        stage('Quality Gate') {
            steps {
                echo 'Vérification du Quality Gate SonarQube...'
                timeout(time: 1, unit: 'HOURS') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        stage('Package') {
            steps {
                echo 'Création du JAR...'
                sh 'mvn package -DskipTests'
            }
        }
    }

    post {
        success {
            echo 'Pipeline réussi !'
        }
        failure {
            echo 'Pipeline échoué.'
        }
        always {
            echo 'Nettoyage de l’espace de travail...'
            cleanWs()
        }
    }
}
