package com.uevitondev.deliveryapp.runner;

import com.uevitondev.deliveryapp.enums.OrderStatus;
import com.uevitondev.deliveryapp.enums.TypeUser;
import com.uevitondev.deliveryapp.model.*;
import com.uevitondev.deliveryapp.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class DatabaseInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PhysicalUserRepository physicalPersonRepository;
    private final LegalUserRepository legalUserRepository;
    private final AddressRepository addressRepository;
    private final CategoryRepository categoryRepository;
    private final StoreRepository storeRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public DatabaseInitializer(UserRepository userRepository, RoleRepository roleRepository,
                               PhysicalUserRepository physicalPersonRepository, LegalUserRepository legalUserRepository,
                               AddressRepository addressRepository, CategoryRepository categoryRepository,
                               StoreRepository storeRepository, ProductRepository productRepository,
                               OrderRepository orderRepository, OrderItemRepository orderItemRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.physicalPersonRepository = physicalPersonRepository;
        this.legalUserRepository = legalUserRepository;
        this.addressRepository = addressRepository;
        this.categoryRepository = categoryRepository;
        this.storeRepository = storeRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        // role
        Role roleAdmin = new Role(1L, "ROLE_ADMIN", LocalDateTime.now());
        Role roleClient = new Role(2L, "ROLE_CLIENT", LocalDateTime.now());
        Role roleSeller = new Role(3L, "ROLE_SELLER", LocalDateTime.now());
        roleRepository.saveAll(List.of(roleAdmin, roleClient, roleSeller));

        // address
        Address addressUser1 = new Address(1L, "03584000", "SP", "São Paulo", "Distrito 1", "Rua da flores", 126);
        addressUser1.setComplement("Condominio Residencial ABA - Apt 26 A");
        addressUser1.setCreatedAt(LocalDateTime.now());
        Address addressStore1 = new Address(2L, "03484000", "MG", "Minas Gerais", "Montes Claros", "Rua da Conservação", 25);
        addressStore1.setComplement("Prédio Comercial, Bloco C, loja 05");
        addressStore1.setCreatedAt(LocalDateTime.now());
        addressRepository.saveAll(List.of(addressUser1, addressStore1));


        // user - role
        PhysicalUser physicalUser1 = new PhysicalUser();
        physicalUser1.setId(1L);
        physicalUser1.setUsername("uevitondev");
        physicalUser1.setName("Ueviton");
        physicalUser1.setLastName("Santos");
        physicalUser1.setEmail("ueviton@gmail.com");
        physicalUser1.setPassword("$2a$10$Y7fk59/1Pg.ig0Goy0yTS.5RgKD18N5J3MYCo5bPYzVpslJqfr4uu");
        physicalUser1.setCreatedAt(LocalDateTime.now());
        physicalUser1.setTypeUser(TypeUser.PHYSICAL);
        physicalUser1.setCpf("02000514236");
        physicalUser1.setEnabled(true);
        physicalUser1.getRoles().addAll(List.of(roleAdmin, roleClient, roleSeller));
        physicalUser1.getAddresses().add(addressUser1);

        PhysicalUser physicalUser2 = new PhysicalUser();
        physicalUser2.setId(2L);
        physicalUser2.setUsername("mariaS_");
        physicalUser2.setName("Maria");
        physicalUser2.setLastName("Silva");
        physicalUser2.setEmail("maria@gmail.com");
        physicalUser2.setPassword("$2a$10$Y7fk59/1Pg.ig0Goy0yTS.5RgKD18N5J3MYCo5bPYzVpslJqfr4uu");
        physicalUser2.setCreatedAt(LocalDateTime.now());
        physicalUser2.setTypeUser(TypeUser.PHYSICAL);
        physicalUser2.setCpf("01563817485");
        physicalUser2.setEnabled(true);
        physicalUser2.getRoles().add(roleClient);
        physicalPersonRepository.saveAll(List.of(physicalUser1, physicalUser2));

        LegalUser legalUser1 = new LegalUser();
        legalUser1.setId(3L);
        legalUser1.setUsername("legaluser");
        legalUser1.setEmail("legaluser@gmail.com");
        legalUser1.setPassword("$2a$10$Y7fk59/1Pg.ig0Goy0yTS.5RgKD18N5J3MYCo5bPYzVpslJqfr4uu");
        legalUser1.setCreatedAt(LocalDateTime.now());
        legalUser1.setTypeUser(TypeUser.LEGAL);
        legalUser1.setFantasyName("Pizzaria Sabor");
        legalUser1.setCnpj("12345678000100");
        legalUser1.setEnabled(true);
        legalUser1.getRoles().add(roleSeller);
        legalUserRepository.save(legalUser1);


        // category - product
        Category category1 = new Category(1L, "PIZZAS", LocalDateTime.now());
        Category category2 = new Category(2L, "BEBIDAS", LocalDateTime.now());
        Category category3 = new Category(3L, "SALGADOS", LocalDateTime.now());
        Category category4 = new Category(4L, "DOCES", LocalDateTime.now());
        categoryRepository.saveAll(List.of(category1, category2, category3, category4));

        // store
        Store store1 = new Store(1L, "Pizzaria Sabor", LocalDateTime.now());
        store1.setUser(legalUser1);
        store1.setAddress(addressStore1);
        Store store2 = new Store(2L, "Restaurante Villa", LocalDateTime.now());
        store2.setUser(legalUser1);
        Store store3 = new Store(3L, "Doceria Braga", LocalDateTime.now());
        store3.setUser(legalUser1);
        Store store4 = new Store(4L, "Adega Bar", LocalDateTime.now());
        store4.setUser(legalUser1);

        storeRepository.saveAll(List.of(store1, store2, store3, store4));

        // product - category - store
        String productImageUrl = "https://images.pexels.com/photos/6941025/pexels-photo-6941025.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1";
        String productDescription = "Desperte seus sentidos com a irresistível Pizza Suprema, uma obra-prima gastronômica que eleva o prazer de saborear uma boa pizza a um novo patamar.";

        Product product1 = new Product(1L, "Greek Pizza", productImageUrl, productDescription, 10.0, LocalDateTime.now());
        product1.setCategory(category1);
        product1.setStore(store1);

        Product product2 = new Product(2L, "Margherita Pizza", productImageUrl, productDescription, 20.0, LocalDateTime.now());
        product2.setCategory(category1);
        product2.setStore(store1);

        Product product3 = new Product(3L, "Coca-Cola 2L", productImageUrl, productDescription, 30.0, LocalDateTime.now());
        product3.setCategory(category2);
        product3.setStore(store1);

        Product product4 = new Product(4L, "Guaraná 1L", productImageUrl, productDescription, 40.0, LocalDateTime.now());
        product4.setCategory(category2);
        product4.setStore(store1);

        Product product5 = new Product(5L, "Esfiha de Frango", productImageUrl, productDescription, 10.0, LocalDateTime.now());
        product5.setCategory(category3);
        product5.setStore(store1);

        Product product6 = new Product(6L, "Coxinha de Frango", productImageUrl, productDescription, 20.0, LocalDateTime.now());
        product6.setCategory(category3);
        product6.setStore(store1);

        Product product7 = new Product(7L, "Pudim de Leite", productImageUrl, productDescription, 30.0, LocalDateTime.now());
        product7.setCategory(category4);
        product7.setStore(store1);

        Product product8 = new Product(8L, "Brigadeiro de Colher", productImageUrl, productDescription, 40.0, LocalDateTime.now());
        product8.setCategory(category4);
        product8.setStore(store1);

        productRepository.saveAll(List.of(product1, product2, product3, product4, product5, product6, product7, product8));

        // order - user - store
        Order order1 = new Order(1L, OrderStatus.PENDENTE, LocalDateTime.now());
        order1.setUser(physicalUser1);
        order1.setStore(store1);
        order1.setAddress(addressUser1);
        order1 = orderRepository.save(order1);

        // orderitem - order
        OrderItem orderItem1 = new OrderItem(1L, 2, "obs", product1, order1);
        orderItem1.calculateOrderItemTotal();
        OrderItem orderItem2 = new OrderItem(2L, 1, "obs", product2, order1);
        orderItem2.calculateOrderItemTotal();
        orderItemRepository.saveAll(List.of(orderItem1, orderItem2));

        order1.getOrderItems().addAll(List.of(orderItem1, orderItem2));
        order1.calculateOrderTotal();
        orderRepository.save(order1);


    }
}
