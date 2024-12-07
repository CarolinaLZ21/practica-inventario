import java.util.List;
import java.util.Scanner;

public class Menu {
    private Inventario inventario;
    private Scanner scanner;

    public Menu() {
        this.inventario = new Inventario();
        this.scanner = new Scanner(System.in);
    }

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("\n--- Menú de Inventario ---");
            System.out.println("1. Agregar producto");
            System.out.println("2. Actualizar producto");
            System.out.println("3. Eliminar producto");
            System.out.println("4. Buscar por categoría");
            System.out.println("5. Generar reporte");
            System.out.println("6. Cantidad de productos por categoría");
            System.out.println("7. Producto más caro");
            System.out.println("8. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    agregarProducto();
                    break;
                case 2:
                    actualizarProducto();
                    break;
                case 3:
                    eliminarProducto();
                    break;
                case 4:
                    buscarPorCategoria();
                    break;
                case 5:
                    inventario.generarReporte();
                    System.out.println("Reporte generado con éxito.");
                    break;
                case 6:
                    cantidadProductosPorCategoria();
                    break;
                case 7:
                    Producto productoMasCaro = inventario.productoMasCaro();
                    if (productoMasCaro != null) {
                        System.out.println("Producto más caro: " + productoMasCaro.getNombre() +
                                ", Precio: " + productoMasCaro.getPrecio());
                    } else {
                        System.out.println("No hay productos en el inventario.");
                    }
                    break;
                case 8:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (opcion != 8);
    }

    private void agregarProducto() {
        System.out.print("ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Categoría: ");
        String categoria = scanner.nextLine();
        System.out.print("Precio: ");
        double precio = scanner.nextDouble();
        System.out.print("Cantidad disponible: ");
        int cantidadDisponible = scanner.nextInt();
        scanner.nextLine();

        Producto nuevoProducto = new Producto(id, nombre, categoria, precio, cantidadDisponible);
        inventario.agregarProducto(nuevoProducto);
        System.out.println("Producto agregado con éxito.");
    }

    private void actualizarProducto() {
        System.out.print("ID del producto a actualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Producto productoActual = inventario.buscarProductoPorId(id);
        if (productoActual != null) {
            System.out.print("Nuevo nombre (actual: " + productoActual.getNombre() + "): ");
            String nombre = scanner.nextLine();
            System.out.print("Nueva categoría (actual: " + productoActual.getCategoria() + "): ");
            String categoria = scanner.nextLine();
            System.out.print("Nuevo precio (actual: " + productoActual.getPrecio() + "): ");
            double precio = scanner.nextDouble();
            System.out.print("Nueva cantidad disponible (actual: " + productoActual.getCantidadDisponible() + "): ");
            int cantidadDisponible = scanner.nextInt();
            scanner.nextLine();

            Producto productoActualizado = new Producto(id, nombre, categoria, precio, cantidadDisponible);
            inventario.actualizarProducto(id, productoActualizado);
            System.out.println("Producto actualizado con éxito.");
        } else {
            System.out.println("Producto no encontrado.");
        }
    }

    private void eliminarProducto() {
        System.out.print("ID del producto a eliminar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        inventario.eliminarProducto(id);
        System.out.println("Producto eliminado con éxito.");
    }

    private void buscarPorCategoria() {
        System.out.print("Categoría: ");
        String categoria = scanner.nextLine();

        List<Producto> productos = inventario.buscarProductosPorCategoria(categoria);
        if (!productos.isEmpty()) {
            for (Producto p : productos) {
                System.out.println("ID: " + p.getId() + ", Nombre: " + p.getNombre() +
                        ", Precio: " + p.getPrecio() + ", Cantidad: " + p.getCantidadDisponible());
            }
        } else {
            System.out.println("No se encontraron productos en la categoría especificada.");
        }
    }

    private void cantidadProductosPorCategoria() {
        System.out.print("Categoría: ");
        String categoria = scanner.nextLine();

        int cantidad = inventario.cantidadDeProductosPorCategoria(categoria);
        System.out.println("Cantidad de productos en la categoría " + categoria + ": " + cantidad);
    }
}
