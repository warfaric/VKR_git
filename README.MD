ШАГ 1. Запуск Docker Desktop

Запусти Docker Desktop

Дождись статуса: “Docker Desktop is running”

Убедись, что используется Linux containers

Проверка в терминале:

docker info | findstr OSType


Ожидаемо:

OSType: linux

ШАГ 2. Переход в корень проекта
cd F:\VKRApp

ШАГ 3. Создание Kubernetes-кластера (kind)

Если кластер ещё не существует:

kind create cluster --name vkr --config k8s/kind-config.yaml


Проверка:

kubectl get nodes


Ожидаемо:

vkr-control-plane   Ready


Если кластер уже существует — этот шаг можно пропустить.

ШАГ 4. Сборка Docker-образов (если нужно)

Если образы не сохранены после перезагрузки:

docker build -t vkr/catalog:1.0 -f catalog-service/Dockerfile . 
docker build -t vkr/admin:1.0   -f admin-service/Dockerfile .


Проверка:

docker images | findstr vkr

ШАГ 5. Загрузка образов в кластер kind
kind load docker-image vkr/catalog:1.0 --name vkr
kind load docker-image vkr/admin:1.0   --name vkr

ШАГ 6. Развёртывание Kubernetes-манифестов
kubectl apply -f k8s/base/


Проверка состояния:

kubectl -n vkr get pods
kubectl -n vkr get svc


Дождись, пока все POD станут:

READY 1/1
STATUS Running

ШАГ 7. Доступ к приложениям
Каталог (публичный сервис)
http://localhost:8081/

Админ-сервис (закрыт, доступ через port-forward)
kubectl -n vkr port-forward svc/admin 8082:8082


В браузере:

http://localhost:8082/admin

ШАГ 8. Завершение работы (по желанию)
Остановить сервисы:
kubectl delete namespace vkr

Или удалить весь кластер:
kind delete cluster --name vkr