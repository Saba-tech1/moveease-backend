# MoveEase Backend — Java Spring Boot + MySQL

Complete REST API backend for the [MoveEase](https://moveeasewithsaba.netlify.app/) moving services platform.

---

## 🛠 Tech Stack

| Layer      | Technology              |
|------------|-------------------------|
| Language   | Java 17                 |
| Framework  | Spring Boot 3.2         |
| Database   | MySQL 8.x               |
| ORM        | Spring Data JPA / Hibernate |
| Security   | Spring Security (CORS)  |
| Build Tool | Maven                   |

---

## 📁 Project Structure

```
moveease-backend/
├── pom.xml
├── database_setup.sql
└── src/main/
    ├── java/com/moveease/
    │   ├── MoveEaseApplication.java       ← Entry point
    │   ├── model/
    │   │   ├── Booking.java               ← Booking entity
    │   │   ├── QuoteRequest.java          ← Quote request entity
    │   │   └── ContactMessage.java        ← Contact form entity
    │   ├── repository/
    │   │   ├── BookingRepository.java
    │   │   ├── QuoteRequestRepository.java
    │   │   └── ContactMessageRepository.java
    │   ├── service/
    │   │   ├── BookingService.java
    │   │   ├── QuoteRequestService.java
    │   │   └── ContactMessageService.java
    │   ├── controller/
    │   │   ├── BookingController.java
    │   │   ├── QuoteController.java
    │   │   └── ContactController.java
    │   └── config/
    │       ├── SecurityConfig.java        ← CORS + Security
    │       └── GlobalExceptionHandler.java
    └── resources/
        └── application.properties
```

---

## ⚙️ Setup Instructions

### Step 1 — Install Prerequisites
- Java 17: https://adoptium.net/
- MySQL 8: https://dev.mysql.com/downloads/
- Maven: https://maven.apache.org/

### Step 2 — Set Up MySQL Database

Open **MySQL Workbench** or MySQL CLI and run:

```bash
mysql -u root -p < database_setup.sql
```

Or paste the contents of `database_setup.sql` into MySQL Workbench and execute.

### Step 3 — Configure `application.properties`

Edit `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/moveease_db?useSSL=false&serverTimezone=UTC
spring.datasource.username=moveease_user       # or root
spring.datasource.password=MoveEase@2024       # your password
```

### Step 4 — Run the Backend

```bash
cd moveease-backend
mvn spring-boot:run
```

Server starts at: **http://localhost:8080**

---

## 🌐 API Endpoints

### Bookings — `/api/bookings`

| Method | Endpoint                     | Description               |
|--------|------------------------------|---------------------------|
| POST   | `/api/bookings`              | Create a new booking      |
| GET    | `/api/bookings`              | List all bookings (admin) |
| GET    | `/api/bookings/{id}`         | Get booking by ID         |
| GET    | `/api/bookings/email/{email}`| Track by customer email   |
| GET    | `/api/bookings/status/{status}` | Filter by status       |
| GET    | `/api/bookings/branch/{branch}` | Filter by branch       |
| PATCH  | `/api/bookings/{id}/status`  | Update booking status     |
| DELETE | `/api/bookings/{id}`         | Delete booking            |

### Quotes — `/api/quotes`

| Method | Endpoint              | Description            |
|--------|-----------------------|------------------------|
| POST   | `/api/quotes`         | Submit quote request   |
| GET    | `/api/quotes`         | All quotes (admin)     |
| GET    | `/api/quotes/new`     | New/unread quotes      |
| PATCH  | `/api/quotes/{id}/status` | Update status      |
| DELETE | `/api/quotes/{id}`    | Delete quote           |

### Contact — `/api/contact`

| Method | Endpoint                  | Description          |
|--------|---------------------------|----------------------|
| POST   | `/api/contact`            | Send contact message |
| GET    | `/api/contact`            | All messages (admin) |
| GET    | `/api/contact/unread`     | Unread messages      |
| PATCH  | `/api/contact/{id}/read`  | Mark as read         |
| DELETE | `/api/contact/{id}`       | Delete message       |

---

## 🔗 Connecting Your Netlify Frontend

In your HTML/JS files on Netlify, replace form submits with fetch calls:

### Example: Submit a Booking

```javascript
async function submitBooking(formData) {
  const response = await fetch('https://YOUR_BACKEND_URL/api/bookings', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({
      fullName: formData.name,
      email: formData.email,
      phone: formData.phone,
      pickupAddress: formData.pickup,
      deliveryAddress: formData.delivery,
      movingDate: formData.date,         // format: "2025-08-15"
      serviceType: "HOUSE_SHIFTING",     // or COURIER / RENTAL_TRANSPORT
      branch: "Chennai",
      additionalNotes: formData.notes
    })
  });

  if (response.ok) {
    const booking = await response.json();
    alert(`Booking confirmed! Your ID: ${booking.id}`);
  }
}
```

### Example: Get a Quote

```javascript
async function requestQuote(formData) {
  const response = await fetch('https://YOUR_BACKEND_URL/api/quotes', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({
      name: formData.name,
      email: formData.email,
      phone: formData.phone,
      fromLocation: formData.from,
      toLocation: formData.to,
      serviceType: "COURIER",
      message: formData.message
    })
  });
  // handle response...
}
```

---

## 🚀 Deploying the Backend

### Option A — Railway (Free, Recommended)
1. Push this project to GitHub
2. Go to https://railway.app → New Project → Deploy from GitHub
3. Add a MySQL service → copy the connection URL
4. Set environment variables for DB credentials
5. Railway gives you a public URL like `https://moveease-backend.up.railway.app`

### Option B — Render
1. Push to GitHub
2. Go to https://render.com → New Web Service
3. Add a MySQL (or PostgreSQL) database
4. Set env vars and deploy

### Option C — AWS / GCP / Azure
Deploy as a JAR on any cloud VM with MySQL RDS.

---

## 📦 Booking Status Flow

```
PENDING → CONFIRMED → IN_PROGRESS → COMPLETED
                ↓
            CANCELLED
```

---

## 🔒 Security Notes

- Public endpoints: POST booking, POST quote, POST contact
- All GET/DELETE/PATCH endpoints require authentication (for admin use)
- CORS is configured to only allow `https://moveeasewithsaba.netlify.app`
- For production, add JWT authentication for admin routes