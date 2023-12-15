Всем привет!

Небольшое приложение-сервис интеграции для работы с банковским сервисом и одновременно клиентский, для получения информаций о лимитах.


Интеграция будет осуществляться via SOAP


<h2>Getting Started</h2>

Для запуска требуется написать 2 команды

docker-compose.yml изменил под формирование джарки с Dockerfile, 
так как я все равно не пушу в репозиторий, лучше уж пусть так

```text
mvn clean install
docker compose up -d --build 
```
<h2>SOAP</h2>
<h3>Пример Запроса</h3>

url: localhost:8085/ws

method: 'POST'

```xml
<soapenv:Envelope
  xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
  xmlns:tran="http://iitu.kz/transaction">
  <soapenv:Header/>
  <soapenv:Body>
    <tran:TransactionRequest>
      <tran:exceed>
        <tran:account_from>4400459683</tran:account_from>
        <tran:account_to>4400303345</tran:account_to>
        <tran:currency_shortname>KZT</tran:currency_shortname>
        <tran:sum>450</tran:sum>
        <tran:expense_category>SERVICE</tran:expense_category>
        <tran:datetime>2023-09-29T10:00:00</tran:datetime>
      </tran:exceed>
    </tran:TransactionRequest>
  </soapenv:Body>
</soapenv:Envelope>
```

<h3>Пример Ответа</h3>

```xml
<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
    <SOAP-ENV:Header/>
    <SOAP-ENV:Body>
        <TransactionResponse xmlns:ns2="http://iitu.kz/transaction">
            <ns2:account_from>4400459683</ns2:account_from>
            <ns2:account_to>4400303345</ns2:account_to>
            <ns2:currency_shortname>USD</ns2:currency_shortname>
            <ns2:sum>0.94</ns2:sum>
            <ns2:expense_category>SERVICE</ns2:expense_category>
            <ns2:datetime>2023-10-04T16:29:09.764152900</ns2:datetime>
            <ns2:limit_sum>1000.0</ns2:limit_sum>
            <ns2:limit_datetime>2023-10-04T16:29:09.736154500</ns2:limit_datetime>
            <ns2:limit_currency_shortname>USD</ns2:limit_currency_shortname>
        </TransactionResponse>
    </SOAP-ENV:Body>
</SOAP-ENV:Envelope>
```
В папке resources можно найти пример запроса, ответа и wsdl
<hr>
<h2>Rest Api</h2>

В проекте присутствует Swagger, /swagger-ui: 

<h3>Первый эндпоинт</h3>

Description: Позволяет изменять лимит для конкретной категории расхода

url: http://localhost:8080/limits/{account_number}?limit={new_limit}&category={GOOD_or_SERVICE}

method: 'POST'

answer:

```json
{
  "success": true
}
```

<h3>Второй эндпоинт</h3>

Description: Возвращает список транзакций, превысивших лимит

url: http://localhost:8085/limits/{account_number}

method: 'GET'

answer:

```json

[
  {
    "id": 407,
    "currency": "USD",
    "sum": 209.51,
    "remainder": -162.2299999999999,
    "expenseCategory": "SERVICE",
    "datetime": "2023-10-04T16:42:23.894033Z",
    "account_number": "4400303345",
    "counterparty_account_number": "4400459683",
    "limit_sum": 550,
    "limit_datetime": "2023-10-04T16:41:29.241999Z",
    "limit_currency": "USD"
  }
]
```

<hr>

<h2>UI-ка приложения</h2>

```text
http://localhost:8080/ui/home
```

<hr>

<h2>Где же происходит Магия?</h2>

<h3>Scheduler</h3>

Отправляет запросы каждое утро в 9:00 на биржу(Можно сменить на время начала работы биржи, либо любое удобное)

В субботу и воскресенье пользователи будут довольствоваться данными с пятницы

А так логика там не сложная

<h3>Сервисы</h3>

- TransactionServiceImpl - сохраняет в бд данные
- ExceedServiceImpl - вычисляет расходы (reminder), изменения в лимитах, в общем тут все вычисления. Не шарющим за матешу рекомендуется не заходить, есть риск почувствовать свою тупость, либо говно код( говорят он до сих пор летает меж строк)

На деле это @BusinessLogic, написать легче мат часть вряд ли удастся