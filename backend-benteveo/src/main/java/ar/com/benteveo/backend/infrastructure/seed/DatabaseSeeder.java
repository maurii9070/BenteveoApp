package ar.com.benteveo.backend.infrastructure.seed;

import ar.com.benteveo.backend.entities.Category;
import ar.com.benteveo.backend.entities.Product;
import ar.com.benteveo.backend.entities.ProductPhoto;
import ar.com.benteveo.backend.entities.Profile;
import ar.com.benteveo.backend.entities.User;
import ar.com.benteveo.backend.enums.ProductStatus;
import ar.com.benteveo.backend.enums.Role;
import ar.com.benteveo.backend.repositories.CategoryRepository;
import ar.com.benteveo.backend.repositories.ProductRepository;
import ar.com.benteveo.backend.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Carga datos de ejemplo (categorías, usuario demo y productos publicados)
 * la primera vez que la aplicación arranca con una base de datos vacía.
 * Es idempotente: si ya existen datos, no hace nada.
 */
@Component
public class DatabaseSeeder implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DatabaseSeeder.class);

    private static final String DEMO_EMAIL = "demo@benteveo.com";
    private static final String DEMO_PASSWORD = "Demo1234";
    private static final String DEMO_DNI = "99999999";

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final PasswordEncoder passwordEncoder;

    public DatabaseSeeder(
            CategoryRepository categoryRepository,
            UserRepository userRepository,
            ProductRepository productRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (categoryRepository.count() > 0 || userRepository.existsByEmail(DEMO_EMAIL)) {
            log.info("Base de datos ya tiene datos de ejemplo. Se omite el seed.");
            return;
        }

        log.info("Cargando datos de ejemplo (seed)...");

        List<Category> categories = categoryRepository.saveAll(List.of(
                category("Herramientas", "herramientas"),
                category("Autos", "autos"),
                category("Maquinaria", "maquinaria"),
                category("Muebles", "muebles"),
                category("Electrodomésticos", "electrodomesticos"),
                category("Tecnología", "tecnologia"),
                category("Bicicletas", "bicicletas"),
                category("Deportes", "deportes")
        ));

        Map<String, Category> categoriesByName = categories.stream()
                .collect(java.util.stream.Collectors.toMap(Category::getName, c -> c));

        User demoUser = createDemoUser();
        log.info("Usuario demo creado: {} / {}", DEMO_EMAIL, DEMO_PASSWORD);

        List<Product> products = List.of(
                product(
                        demoUser,
                        "Taladro percutor Bosch",
                        "Taladro percutor de 750W ideal para muros y hormigón. Incluye maletín, juego de mechas y disco diamantado. Perfecto para proyectos hogareños.",
                        "12000", "45000", "150000", "15000",
                        categoriesByName.get("Herramientas"),
                        "taladro-percutor-bosch", "Herramienta en excelente estado. Se entrega con batería extra y cargador.",
                        "4.8", 23),
                product(
                        demoUser,
                        "Sierra circular Makita",
                        "Sierra circular de 1400W con guía láser. Ideal para carpintería y cortes precisos. Incluye 2 discos y llave de ajuste.",
                        "15000", "55000", "180000", "18000",
                        categoriesByName.get("Herramientas"),
                        "sierra-circular-makita", "Corte limpio y potente. Incluye manual de usuario.", "4.6", 15),
                product(
                        demoUser,
                        "Bicicleta MTB Rodado 29",
                        "Bicicleta mountain bike rodado 29 con 21 velocidades, frenos a disco y suspensión delantera. Ideal para ciudad y caminos.",
                        "8000", "28000", "90000", "12000",
                        categoriesByName.get("Bicicletas"),
                        "bicicleta-mtb-rodado-29", "Bicicleta en buen estado, recién tuneada.", "4.9", 31),
                product(
                        demoUser,
                        "Compresor de aire 50L",
                        "Compresor de aire de 50 litros con 2 HP, ideal para inflar neumáticos, pintar y herramientas neumáticas. Incluye manguera y acoples.",
                        "18000", "65000", "210000", "20000",
                        categoriesByName.get("Herramientas"),
                        "compresor-aire-50l", "Compresor potente y silencioso. Listo para usar.", "4.5", 9),
                product(
                        demoUser,
                        "Cortadora de césped a nafta",
                        "Cortadora de césped a nafta 4 tiempos con bolsa recolectora. Ideal para jardines medianos. Incluye combustible de prueba.",
                        "14000", "50000", "160000", "15000",
                        categoriesByName.get("Maquinaria"),
                        "cortadora-cesped-nafta", "Arranca a la primera. Se entrega afilada y con aceite nuevo.", "4.7", 18),
                product(
                        demoUser,
                        "Escalera de aluminio 6 metros",
                        "Escalera de aluminio extensible de 6 metros, con doble traba de seguridad. Ideal para tareas de altura en casa u obra.",
                        "5000", "18000", "55000", "8000",
                        categoriesByName.get("Herramientas"),
                        "escalera-aluminio-6m", "Liviana y resistente. Incluye funda de transporte.", "4.4", 12)
        );

        productRepository.saveAll(products);
        log.info("Seed completado: {} categorías, 1 usuario demo, {} productos publicados.",
                categories.size(), products.size());
    }

    private User createDemoUser() {
        var user = User.builder()
                .email(DEMO_EMAIL)
                .password(passwordEncoder.encode(DEMO_PASSWORD))
                .dni(DEMO_DNI)
                .roles(List.of(Role.USER))
                .isActive(true)
                .emailVerified(true)
                .build();

        var profile = Profile.builder()
                .firstName("Demo")
                .lastName("Benteveo")
                .user(user)
                .isComplete(true)
                .build();

        user.setProfile(profile);
        return userRepository.save(user);
    }

    private Category category(String name, String slug) {
        return Category.builder()
                .name(name)
                .slug(slug)
                .build();
    }

    private Product product(
            User owner,
            String title,
            String description,
            String priceDay,
            String priceWeek,
            String priceMonth,
            String deposit,
            Category category,
            String slug,
            String photoCaption,
            String ratingAvg,
            int ratingCount
    ) {
        var product = Product.builder()
                .title(title)
                .description(description)
                .priceDay(new BigDecimal(priceDay))
                .priceWeek(new BigDecimal(priceWeek))
                .priceMonth(new BigDecimal(priceMonth))
                .deposit(new BigDecimal(deposit))
                .isActive(true)
                .status(ProductStatus.PUBLISHED)
                .ratingAvg(new BigDecimal(ratingAvg))
                .ratingCount(ratingCount)
                .owner(owner)
                .category(category)
                .build();

        var photo = ProductPhoto.builder()
                .url("https://picsum.photos/seed/%s/800/600".formatted(slug))
                .publicId("seed/" + slug + "-1")
                .caption(photoCaption)
                .sortOrder(0)
                .isPrimary(true)
                .product(product)
                .build();

        product.setPhotos(List.of(photo));
        return product;
    }
}
