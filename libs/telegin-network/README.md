# Telegin Weather Network Module

Модуль для работы с OpenWeatherMap API.

## Настройка API ключа

Для работы модуля требуется API ключ от OpenWeatherMap.

### Как получить API ключ:

1. Перейдите на сайт [OpenWeatherMap](https://openweathermap.org/api)
2. Зарегистрируйтесь или войдите в аккаунт
3. Перейдите в раздел [API keys](https://home.openweathermap.org/api_keys)
4. Создайте новый API ключ (или используйте существующий)
5. Скопируйте полученный ключ

### Как добавить ключ в проект:

1. Откройте файл `local.properties` в корне проекта
2. Добавьте строку:
   ```
   OPEN_WEATHER_API_KEY=ваш_api_ключ_здесь
   ```
3. Замените `ваш_api_ключ_здесь` на ваш реальный API ключ
4. Пересоберите проект

### Пример:

```properties
sdk.dir=/path/to/android/sdk
OPEN_WEATHER_API_KEY=1234567890abcdef1234567890abcdef
```

