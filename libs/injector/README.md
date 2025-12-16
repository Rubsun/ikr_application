# Библиотека Injector

Этот модуль предоставляет гибкий способ реализации и регистрации инициализаторов приложения/сервиса в модульной архитектуре с помощью класса `AbstractInitializer`. Используйте его как основу для конфигурации фич, внедрения зависимостей или запуска логики между фичами в многомодульных Android-проектах.

## Как создать свой инициализатор

1. **Создайте класс, который наследуется от `AbstractInitializer`:**

```kotlin
import com.example.injector.AbstractInitializer

class MyFeatureInitializer : AbstractInitializer() {
    override fun initialize(context: Context) {
        // Ваша логика инициализации
    }
}
```

2. **Зарегистрируйте ваш инициализатор:**

Добавьте запись в `AndroidManifest.xml` модуля с помощью `<provider>` или `<meta-data>` (в зависимости от способа регистрации). Например:

```xml
...
    <application>
        <provider
            android:name="androidx.startup.InitializationProvider"
            android:authorities="${applicationId}.androidx-startup"
            android:exported="false"
            tools:node="merge">
            <!-- Регистрируем инициализатор вашей фичи -->
            <meta-data
                android:name="com.example.injector.MyFeatureInitializer"
                android:value="androidx.startup" />
            <!-- Если требуется, регистрируйте другие инициализаторы так же -->
        </provider>
    </application>
...
```

Если используется специальный утилитарный класс для регистрации, следуйте принятой в проекте конвенции. Убедитесь, что ваш инициализатор обнаруживается Injector при старте приложения.

## Лучшие практики

- Используйте инициализаторы для выноса логики конфигурирования/инициализации из `Application` или корневых модулей.
- Регистрируйте только один инициализатор на одну фичу/модуль для прозрачного контроля процесса запуска.
- Избегайте тяжёлых/блокирующих операций в `initialize()`, чтобы не тормозить старт приложения.

---
