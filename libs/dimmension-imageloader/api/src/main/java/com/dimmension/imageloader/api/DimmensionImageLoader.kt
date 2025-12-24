package com.dimmension.imageloader.api

/**
 * Интерфейс для загрузки изображений.
 * Абстрагирует конкретную реализацию (Coil, Glide, Picasso и т.д.)
 */
interface DimmensionImageLoader {
    /**
     * Загружает изображение по URL в указанный View
     * @param view целевой View (должен быть ImageView)
     * @param url URL изображения
     */
    fun load(view: Any, url: String)

    /**
     * Загружает изображение с placeholder и error изображениями
     * @param view целевой View (должен быть ImageView)
     * @param url URL изображения
     * @param placeholderResId ресурс placeholder изображения
     * @param errorResId ресурс изображения при ошибке
     */
    fun load(view: Any, url: String, placeholderResId: Int?, errorResId: Int?)
}

