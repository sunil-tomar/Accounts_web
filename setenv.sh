echo "Environment variables required for this micro-service can be exported below."

export ACCOUNTS_DB_DRIVER_CLASS_NAME="org.postgresql.Driver"
export ACCOUNTS_DB_DIALECT="org.hibernate.dialect.PostgreSQL95Dialect"
export ACCOUNTS_DB_URL="jdbc:postgresql://localhost:5001/ehubdb?currentSchema=ehub&stringtype=unspecified"
export ACCOUNTS_DB_USERNAME="ehubdb"
export ACCOUNTS_DB_PASSWORD="ehubdb"
export ACCOUNTS_HISTORY_TOPIC="aeps_new_txn_topic"
export ACCOUNTS_HISTORY_CONSUMER_GROUP="aeps-consumer-group"
export ACCOUNTS_HISTORY_BOOTSTRAP_SERVERS="localhost:9092"
export KAFKA_ENABLED="false"
export KAFKA_PRODUCER_TEST_ENABLED="false"
export KAFKA_PRODUCER_TEST_TOPIC_MESSAGE_SIZE=10000


echo "ACCOUNTS_DB_DRIVER_CLASS_NAME : ${ACCOUNTS_DB_DRIVER_CLASS_NAME}"
echo "ACCOUNTS_DB_DIALECT : ${ACCOUNTS_DB_DIALECT}"
echo "ACCOUNTS_DB_URL : ${ACCOUNTS_DB_URL}"
echo "ACCOUNTS_DB_USERNAME : ${ACCOUNTS_DB_USERNAME}"
echo "ACCOUNTS_DB_PASSWORD : ********"
echo "ACCOUNTS_HISTORY_TOPIC : ${ACCOUNTS_HISTORY_TOPIC}"
echo "ACCOUNTS_HISTORY_CONSUMER_GROUP : ${ACCOUNTS_HISTORY_CONSUMER_GROUP}"
echo "ACCOUNTS_HISTORY_BOOTSTRAP_SERVERS : ${ACCOUNTS_HISTORY_BOOTSTRAP_SERVERS}"
echo "KAFKA_ENABLED : ${KAFKA_ENABLED}"

echo "Environment variables exported "