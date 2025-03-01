pipeline {
    agent any // Jenkins가 사용 가능한 어떤 에이전트에서도 파이프라인을 실행할 수 있도록 지정합니다.

    environment {
        DEPLOY_DIR = './vol/deployment' // 도커 컴포즈 파일에서 마운트한 로컬 배포 폴더에 대응되는 컨테이너 내부 경로입니다.
        JAR_PATH = './build/libs/prac-jpa-0.0.1-SNAPSHOT.jar' // 빌드 후 생성되는 jar 파일의 상대 경로입니다.
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: feature/jenkins-test
            }
        }

        stage('Build & Test') {
            steps {
                sh './gradlew clean build'
            }
        }

        stage('Deploy') {
            when {
                expression { return env.GIT_BRANCH != 'main' } // main 브랜치가 아닐 때만 배포를 수행 (테스트용 빌드로 배포)
            }
            steps {
                sh "cp ${JAR_PATH} ${DEPLOY_DIR}/" // 빌드 산출물(jar 파일)을 로컬 배포 폴더로 복사

                // jar 파일을 백그라운드 실행 (이미 실행 중인 경우 기존 프로세스 종료 후 재실행 로직 추가 고려)
                sh "nohup java -jar ${DEPLOY_DIR}/simple-ddd.jar > ${DEPLOY_DIR}/app.log 2>&1 &"
            }
        }
    }

    // 빌드나 배포 과정에서 문제가 발생할 경우 실패 메시지를 출력하여 문제를 쉽게 파악할 수 있도록 돕습니다.
    post {
        failure {
            echo "Build or deployment failed. Please check the logs."
        }
    }
}
