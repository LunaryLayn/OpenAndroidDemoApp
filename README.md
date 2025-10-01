# PokeApp

ENG: This project is another showcase in my portfolio, designed for recruiters to see how I approach different technologies. The app allows users to browse a list of Pokémon, filter them by name or type, and view a detailed screen with additional information. While my **CryptoWatcher** app demonstrates working with **REST (Retrofit) and MVVM**, this project focuses on exploring **GraphQL (Apollo), pagination, MVI, and animations with Lottie**.  

## Features

- **Pokémon List**: Displays a list of Pokémon retrieved from a GraphQL API.  
- **Filtering Options**: Users can filter Pokémon by name or type.  
- **Detail Screen**: Tapping a Pokémon opens a detail page with extended information.  
- **Pagination Support**: Efficiently loads Pokémon data in pages to optimize performance.  
- **Animations with Lottie**: Smooth animations enhance the UI experience.  
- **MVI with Clean Architecture**: Demonstrates a structured state-management approach different from the MVVM showcase in CryptoWatcher.  

> ⚠️ Note: This project intentionally does **not include** some aspects that are instead demonstrated in **[CryptoWatcher](https://github.com/LunaryLayn/CryptoWatcher)**:  
> - **Unit Tests** (with Mockito + JUnit).  
> - **Error handling and visualization in the UI**.  
> - **MVVM pattern with REST (Retrofit)** instead of MVI with GraphQL.  
>   
> If you want to see those features in action, please check **[CryptoWatcher](https://github.com/LunaryLayn/CryptoWatcher)**.  

## Tech Stack

### Core Technologies

- **Kotlin**: The primary language for Android development.  
- **Jetpack Compose**: Declarative UI toolkit.  
- **MVI with Clean Architecture**: For predictable and maintainable state management.  
- **Coroutines**: For handling asynchronous tasks.  
- **Hilt**: Dependency injection.  
- **Apollo (GraphQL)**: For consuming GraphQL endpoints.  
- **Pagination**: To handle large datasets efficiently.  
- **Lottie**: For adding animations to the UI.  

## Architecture

This project follows the **MVI** (Model-View-Intent) pattern within a **Clean Architecture** structure. It ensures separation of concerns and predictable state handling:  

- **Domain Module**: Business logic and use cases.  
- **Data Module**: Fetches data from the GraphQL API using Apollo.  
- **Presentation Layer**: Jetpack Compose UI with Lottie animations, powered by MVI state flows.  

IMAGES AT THE END OF THIS PRESENTATION.  

---

ESP: # PokeApp

Este proyecto es otro showcase en mi portfolio, diseñado para que los reclutadores vean cómo abordo diferentes tecnologías. La aplicación permite ver una lista de Pokémon, filtrarlos por nombre o tipo, y acceder a una pantalla de detalle con más información. Mientras que **CryptoWatcher** muestra mi trabajo con **REST (Retrofit) y MVVM**, este proyecto se centra en **GraphQL (Apollo), paginación, MVI y animaciones con Lottie**.  

## Funcionalidades

- **Lista de Pokémon**: Muestra una lista obtenida desde una API GraphQL.  
- **Opciones de filtrado**: Los usuarios pueden filtrar por nombre o tipo.  
- **Pantalla de detalle**: Al seleccionar un Pokémon, se accede a información más detallada.  
- **Soporte de paginación**: Carga de datos por páginas de manera eficiente.  
- **Animaciones con Lottie**: Animaciones fluidas que mejoran la experiencia visual.  
- **MVI con Clean Architecture**: Muestra un enfoque estructurado de manejo de estado, diferente al showcase de MVVM en CryptoWatcher.  

> ⚠️ Nota: Este proyecto intencionalmente **no incluye** ciertos aspectos que se muestran en **[CryptoWatcher](https://github.com/LunaryLayn/CryptoWatcher)**:  
> - **Pruebas unitarias** (con Mockito + JUnit).  
> - **Manejo y visualización de errores en la UI**.  
> - **Patrón MVVM con REST (Retrofit)** en lugar de MVI con GraphQL.  
>   
> Si quieres ver estas características en acción, revisa **[CryptoWatcher](https://github.com/LunaryLayn/CryptoWatcher)**.  

## Stack Tecnológico

### Tecnologías principales

- **Kotlin**: Lenguaje principal para Android.  
- **Jetpack Compose**: Toolkit declarativo para UI.  
- **MVI con Clean Architecture**: Para un manejo de estado predecible y mantenible.  
- **Coroutines**: Manejo de asincronía.  
- **Hilt**: Inyección de dependencias.  
- **Apollo (GraphQL)**: Consumo de endpoints GraphQL.  
- **Paginación**: Para manejar grandes conjuntos de datos eficientemente.  
- **Lottie**: Para añadir animaciones a la interfaz.  

## Arquitectura

Este proyecto sigue el patrón **MVI** (Model-View-Intent) dentro de una estructura de **Clean Architecture**, asegurando separación de responsabilidades y manejo de estado predecible:  

- **Módulo de Dominio**: Lógica de negocio y casos de uso.  
- **Módulo de Datos**: Obtiene datos desde la API GraphQL mediante Apollo.  
- **Capa de Presentación**: Interfaz con Jetpack Compose y animaciones Lottie, gestionada por flujos de estado MVI.  

IMAGES AT THE END OF THIS PRESENTATION.  
