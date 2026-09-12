# 🚀 Spring Boot & Java Learning Progress Tracker

**Project**: `employee-management-api`  
**Stack**: Java 17 | Spring Boot 4.x / 3.x | Spring Data JPA | H2 Database  
**Learner Profile**: Experienced Frontend/Full-Stack Engineer expanding into enterprise backend engineering.  
**Core Goal**: Deep understanding of backend architecture, persistence mechanics, interview readiness, and production trade-offs over memorizing annotations.

---

## 🧭 How We Learn (Teaching Methodology)

Every concept follows a strict 6-step cycle to ensure genuine comprehension and practical muscle memory:
1. **Mental Model & Theory**: Explain *why* the concept exists, how it works under the hood, and critical pitfalls.
2. **Focused Example**: Minimal, targeted code syntax demonstrating the mechanism.
3. **Conceptual Check**: 1–2 questions to test understanding before touching code.
4. **Hands-on Task**: One concrete feature/refactor implemented directly in `employee-management-api`.
5. **Code Review**: Evaluate correctness first, then discuss production nuances and interview edge cases.
6. **Move Forward**: Advance only when the current concept is solidly understood.

---

## 📋 Comprehensive Learning Roadmap

### Phase 1: Core Java & Build Tools
- [x] **Java Memory Visibility & Concurrency**
  - [x] Memory model: `volatile` vs `synchronized`
  - [x] `Runnable` vs `Callable` & `Future`
  - [x] `ExecutorService` thread pools and lifecycle (`shutdown()`)
- [x] **Maven Fundamentals**
  - [x] `pom.xml`, coordinates (`groupId`, `artifactId`, `version`)
  - [x] Build lifecycle (`compile`, `test`, `package`, `install`)
  - [x] Dependency scopes (`compile`, `runtime`, `test`)
- [x] **Plain Java OOP Bridge**
  - [x] Polymorphism, abstract classes, interfaces
  - [x] In-memory repository with `HashMap`, Streams, `Optional`
  - [x] Manual constructor-based Dependency Injection (the bridge to Spring IoC)

---

### Phase 2: Spring Boot Fundamentals & REST API
- [x] **Spring IoC Container & Dependency Injection**
  - [x] `ApplicationContext`, Bean lifecycle, Component scanning
  - [x] Stereotypes: `@Component`, `@Service`, `@RestController`
  - [x] Constructor injection best practices (immutability with `final`)
- [x] **RESTful Web Services**
  - [x] `@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping`
  - [x] `@PathVariable`, `@RequestParam`, `@RequestBody`
  - [x] HTTP response status codes & `ResponseEntity`
- [x] **Input Validation**
  - [x] Bean Validation with `@Valid`
  - [x] String constraints (`@NotBlank`) vs Object constraints (`@NotNull`)
- [x] **Centralized Exception Handling**
  - [x] `@RestControllerAdvice` and `@ExceptionHandler`
  - [x] Consistent error payloads and HTTP status mappings (`400`, `404`, `409`, `500`)
- [x] **DTO (Data Transfer Object) Pattern**
  - [x] Separating API contracts (Java `record`s) from JPA persistence entities
  - [x] Preventing accidental data leakage and unintended lazy-query triggers

---

### Phase 3: Spring Data JPA & Relationships
- [x] **JPA & Hibernate Foundations**
  - [x] `@Entity`, `@Id`, `@GeneratedValue(strategy = GenerationType.IDENTITY)`
  - [x] `JpaRepository` abstraction
  - [x] Derived query methods (`findByName`, `findBySalaryGreaterThan`, etc.)
- [x] **Entity Associations**
  - [x] `@ManyToOne`: `Employee` $\rightarrow$ `Department` (`department_id` FK)
  - [x] `@ManyToMany`: `Employee` $\leftrightarrow$ `Skill` with `@JoinTable` (`employee_skill`)
  - [x] Owning side vs Inverse side (`mappedBy`)
- [x] **Transaction Management**
  - [x] Atomic units of work with `@Transactional`
  - [x] Service layer as the natural transaction boundary

---

### Phase 4: Query Optimization & The N+1 Problem
- [x] **Diagnosing N+1 Query Problem**
  - [x] Inspecting queries with SQL logging (`spring.jpa.show-sql=true`)
  - [x] Why global `FetchType.EAGER` is an anti-pattern
  - [x] Association defaults: keeping associations `FetchType.LAZY`
- [x] **Intentional Fetching (Single-Valued Associations)**
  - [x] `JOIN FETCH` vs regular `JOIN`
  - [x] `@EntityGraph` mechanism and Spring Data method parsing constraints
  - [x] Hands-on: `GET /employees/details` fetching `department` in 1 query
- [x] **Intentional Fetching (Collection Associations)**
  - [x] `LEFT JOIN FETCH` for collections (`e.skills`)
  - [x] Preserving parent entities with 0 children (outer joins)
  - [x] Hands-on: `GET /employees/skills-summary`
- [x] **Hibernate 5 vs Hibernate 6 Deduplication**
  - [x] Why Hibernate 6 auto-deduplicates in-memory for `JOIN FETCH`
  - [x] When `DISTINCT` is still required (plain `JOIN` filtering, scalar/DTO projections)
- [ ] **The Cartesian Product Hazard & `MultipleBagFetchException`**
  - [ ] Why fetching multiple collections in one query fails/explodes
  - [ ] Mitigation strategies (two-step queries, `Set` vs `List`)

---

### Phase 5: Persistence Context & Entity Lifecycle *(Up Next)*
- [ ] **Entity Lifecycle States**
  - [ ] Transient, Managed, Detached, Removed
- [ ] **First-Level Cache (`EntityManager`)**
  - [ ] How the Persistence Context tracks entities within a transaction
- [ ] **Dirty Checking (Automatic Persistence)**
  - [ ] How Hibernate detects field mutations
  - [ ] Why `repository.save()` is often redundant on managed entities
- [ ] **`LazyInitializationException`**
  - [ ] What triggers it, why OSIV (Open Session in View) is controversial, and how to avoid it properly

---

### Phase 6: Advanced JPA & Data Modeling
- [ ] **Cascade Operations & Lifecycle Coupling**
  - [ ] `CascadeType.ALL`, `PERSIST`, `MERGE`, etc.
  - [ ] `orphanRemoval = true` vs `CascadeType.REMOVE`
- [ ] **Bidirectional Association Management**
  - [ ] Helper synchronization methods (`addSkill`, `removeSkill`)
  - [ ] Preventing infinite loops in `toString()`, `equals()`, and `hashCode()`
- [ ] **Custom Queries & Projections**
  - [ ] Complex JPQL with `@Query`
  - [ ] Interface-based and Record-based Spring Data Projections
- [ ] **Pagination & Sorting**
  - [ ] `Pageable`, `PageRequest`, `Sort`
  - [ ] Pagination with `JOIN FETCH` caveats (in-memory pagination warnings)
- [ ] **Performance & Indexes**
  - [ ] Adding `@Table(indexes = ...)`
  - [ ] Reading SQL execution plans

---

### Phase 7: Production Architecture & Enterprise Readiness
- [ ] **Transaction Propagation & Isolation**
  - [ ] `Propagation.REQUIRED` vs `REQUIRES_NEW`
  - [ ] Concurrency phenomena (Dirty Reads, Non-repeatable Reads, Phantom Reads)
- [ ] **Configuration & Profiles**
  - [ ] `application-dev.yml` vs `application-prod.yml`
  - [ ] Migrating from H2 to PostgreSQL / MySQL with Flyway or Liquibase
- [ ] **Testing Strategy (Test Slices)**
  - [ ] Unit testing services with Mockito (`@Mock`, `@InjectMocks`)
  - [ ] Slice testing controllers with `@WebMvcTest`
  - [ ] Repository testing with `@DataJpaTest` / Testcontainers
- [ ] **Spring Security**
  - [ ] Filter chain architecture
  - [ ] Stateless authentication with JWT (JSON Web Tokens)
  - [ ] Role-Based Access Control (RBAC) with `@PreAuthorize`
