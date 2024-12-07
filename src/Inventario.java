import java.io.*;
import java.util.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Inventario {
    private List<Producto> productos;

    // Constructor
    public Inventario() {
        this.productos = new ArrayList<>();
        cargarProductos("productos.txt");
    }

    private void cargarProductos(String rutaArchivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");
                Producto producto = new Producto(
                        Integer.parseInt(datos[0]),
                        datos[1],
                        datos[2],
                        Double.parseDouble(datos[3].trim().replace(',', '.')),
                        Integer.parseInt(datos[4])
                );
                productos.add(producto);
            }
        } catch (IOException e) {
            System.out.println("Error al cargar productos: " + e.getMessage());
        }
    }


    // Métodos para agregar, actualizar, eliminar y buscar productos

    public void agregarProducto(Producto producto) {
        productos.add(producto);
        guardarProductos();
    }

    public void actualizarProducto(int id, Producto productoActualizado) {
        for (Producto p : productos) {
            if (p.getId() == id) {
                p.setNombre(productoActualizado.getNombre());
                p.setCategoria(productoActualizado.getCategoria());
                p.setPrecio(productoActualizado.getPrecio());
                p.setCantidadDisponible(productoActualizado.getCantidadDisponible());
                guardarProductos();
                return;
            }
        }
        System.out.println("Producto no encontrado.");
    }

    public void eliminarProducto(int id) {
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getId() == id) {
                productos.remove(i);
                guardarProductos();
                return;
            }
        }
        System.out.println("Producto no encontrado.");
    }



    public Producto buscarProductoPorId(int id) {
        for (Producto p : productos) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    public List<Producto> buscarProductosPorCategoria(String categoria) {
        List<Producto> resultado = new ArrayList<>();
        for (Producto p : productos) {
            if (p.getCategoria().equalsIgnoreCase(categoria)) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    public Producto productoMasCaro() {
        Producto masCaro = null;

        for (Producto p : productos) {
            if (masCaro == null || p.getPrecio() > masCaro.getPrecio()) {
                masCaro = p;
            }
        }

        return masCaro;
    }


    public int cantidadDeProductosPorCategoria(String categoria) {
        int cantidad = 0;
        for (Producto p : productos) {
            if (p.getCategoria().equalsIgnoreCase(categoria)) {
                cantidad++;
            }
        }
        return cantidad;
    }

    public void generarReporte() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("reporte_inventario.txt"))) {
            double valorTotalInventario = 0;
            for (Producto p : productos) {
                double valorProducto = p.getPrecio() * p.getCantidadDisponible();
                valorTotalInventario += valorProducto;
                bw.write(String.format("ID: %d, Nombre: %s, Categoría: %s, Precio: %.2f, Cantidad: %d, Valor: %.2f%n",
                        p.getId(), p.getNombre(), p.getCategoria(), p.getPrecio(), p.getCantidadDisponible(), valorProducto));
            }
            bw.write("Valor total del inventario: " + valorTotalInventario);
        } catch (IOException e) {
            System.out.println("Error al generar el reporte: " + e.getMessage());
        }
    }

    // Método para guardar productos en el archivo
    private void guardarProductos() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("productos.txt"))) {
            for (Producto p : productos) {
                bw.write(String.format("%d;%s;%s;%.2f;%d%n",
                        p.getId(), p.getNombre(), p.getCategoria(), p.getPrecio(), p.getCantidadDisponible()));
            }
        } catch (IOException e) {
            System.out.println("Error al guardar productos: " + e.getMessage());
        }
    }
}

