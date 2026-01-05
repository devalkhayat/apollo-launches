# 🚀 Apollo Launches App

An Android application built with **Kotlin** and **Jetpack Compose**, consuming a **GraphQL API** using **Apollo Kotlin**, and implementing **cursor-based pagination** with **Paging 3**.

This project was developed as a technical task, focusing on clean code, proper architecture, and modern Android practices.

---

## ✨ Features

- GraphQL integration using **Apollo Kotlin 3**
- Cursor-based pagination
- Jetpack Compose UI
- Clean Architecture (Data / Domain / Presentation)
- MVI pattern
- Multi-language support (English / Arabic)
- Material 3 UI

---

## 🏗 Architecture

The project follows **Clean Architecture**:

```
presentation → domain → data
```

- **Presentation**: Compose UI + ViewModel (MVI)
- **Domain**: UseCases, models, repository interfaces
- **Data**: GraphQL service, DTOs, mappers, PagingSource

---

## 🔄 Pagination

Pagination is implemented using **cursor-based paging** provided by the GraphQL API and integrated with **Paging 3** through a custom `PagingSource`.

---

## 🛠 Tech Stack

- Kotlin
- Jetpack Compose
- Apollo Kotlin (GraphQL)
- Paging 3
- Coroutines & Flow
- Hilt
- Material 3

---

## 👨‍💻 Author

**Mohammed Alkhayat**  
Senior Mobile Developer (Android / Flutter)

## Sample
https://github.com/user-attachments/assets/c7934bbe-ef73-4e72-93b0-26756f6332e5



