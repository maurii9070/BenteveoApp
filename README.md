# 🐦 Benteveo 

> **Plataforma comunitaria de alquiler de objetos entre vecinos.**
> Fomentando la economía circular, la confianza y el ahorro local.

Benteveo es una aplicación web que permite a los usuarios publicar herramientas, artículos de camping, tecnología y cualquier objeto de uso esporádico para alquilarlo a otros vecinos de su zona de forma segura y estructurada.

---

## 🚀 Funcionalidades Principales

* **Autenticación Segura:** Registro e inicio de sesión de usuarios con validación de identidad.
* **Catálogo Dinámico:** Exploración de productos disponibles filtrados por zona y disponibilidad.
* **Gestión de Turnos:** Sistema de reservas con validación de fechas (sin superposiciones) y cálculo automático de costos.
* **Panel de Control (Dashboard):** Interfaz para que cada usuario administre sus objetos publicados y sus alquileres en curso (estados: *Pendiente, Confirmado, Finalizado, Cancelado*).
* **Sistema de Reputación:** Calificaciones cruzadas (1 a 5 estrellas) y reseñas post-alquiler para garantizar la seguridad de la comunidad.
* **Almacenamiento en la Nube:** Subida de imágenes de productos optimizada.

---

## 🛠️ Stack Tecnológico



### Frontend
* **Framework:** Angular 22
* **Lenguaje:** TypeScript
* **Estilos:** CSS3 / HTML5 (o detallar si usan Tailwind/Bootstrap)

### Backend
* **Lenguaje:** Java 21
* **Framework:** (Ej. Spring Boot 3.x)
* **Almacenamiento de Archivos:** Cloudinary 
* **Base de Datos:** Relational DB (PostgresSQL)

---

## 🏗️ Arquitectura del Proyecto

Benteveo utiliza un modelo **Multirepo** para garantizar el aislamiento de entornos, evitar conflictos de integración y facilitar el despliegue.

* **Frontend:** Encargado de la UI/UX, consumo de la API REST y manejo del estado global de la aplicación.
* **Backend:** Expone la API RESTful, gestiona la lógica de reservas, los permisos de los roles, las transacciones a la base de datos y la subida de imágenes a Supabase.

---

## ⚙️ Instalación y Configuración Local

### Prerrequisitos
* Node.js y npm/pnpm instalados.
* Java 21 (JDK) instalado.
* Base de datos local configurada.

### Levantar el Backend (Java 21)
1. Clonar el repositorio del backend.
2. Configurar las variables de entorno relativas a la base de datos y las credenciales de Supabase.
3. Compilar y ejecutar el proyecto (ej. vía Maven o Gradle):
   ```bash
   ./mvnw spring-boot:run
