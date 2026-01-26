pipeline {
    agent any

    tools {
        maven 'Maven-3.9' // Assure-toi que Maven est configuré dans Jenkins
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
                echo 'Analyse SonarQube avec Maven...'
                // Utilise le scanner SonarQube configuré dans Jenkins
                withSonarQubeEnv('SonarQube') {
                    sh "mvn sonar:sonar \
                        -Dsonar.projectKey=calculatrice \
                        -Dsonar.host.url=http://localhost:9000 \
                        -Dsonar.login=TON_TOKEN_ICI"
                }
            }
        }

        stage('Quality Gate') {
            steps {
                echo 'Vérification du Quality Gate...'
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
