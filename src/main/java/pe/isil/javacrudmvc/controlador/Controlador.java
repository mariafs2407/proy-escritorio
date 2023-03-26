/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.isil.javacrudmvc.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.html.CSS;
import pe.isil.javacrudmvc.DAO.AD_Persona;
import pe.isil.javacrudmvc.modelo.Persona;
import pe.isil.javacrudmvc.vista.vista;

/**
 *
 * @author maria
 */
public class Controlador implements ActionListener {

    AD_Persona ad_persona = new AD_Persona();
    Persona persona = new Persona();
    vista vista = new vista();
    DefaultTableModel modelo = new DefaultTableModel();

    public Controlador(vista v) {
        //1.primero agregamos el boton del evento
        this.vista = v;
        this.vista.btnListar.addActionListener(this);
        this.vista.btnGuardar.addActionListener(this);
        this.vista.btnEditar.addActionListener(this);
        this.vista.btnActualizar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
        listar(vista.tabla);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //5.colocamos el evento para cuando el usuario presione el boton
        if (e.getSource() == vista.btnListar) {
            listar(vista.tabla);//para mostrar los datos en la tabla
        }
        if (e.getSource() == vista.btnGuardar) {
            insertar();
            limpiarTabla();
            listar(vista.tabla);
        }
        if (e.getSource() == vista.btnEditar) {
            int fila = vista.tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(vista, "Debe seleccionar una fila");
            } else {
                //mostrar en los texbox todos los datos de persona
                int id = Integer.parseInt((String) vista.tabla.getValueAt(fila, 0).toString());
                String nombres = (String) vista.tabla.getValueAt(fila, 1).toString();
                String correo = (String) vista.tabla.getValueAt(fila, 2).toString();
                int telefono = Integer.parseInt((String) vista.tabla.getValueAt(fila, 3).toString());

                vista.txtId.setText("" + id);
                vista.txtNombre.setText(nombres);
                vista.txtCorreo.setText(correo);
                vista.txtTelefono.setText("" + telefono);
            }
        }

        if (e.getSource() == vista.btnActualizar) {
            actualizar();
            limpiarTabla();
            listar(vista.tabla);
        }

        if (e.getSource() == vista.btnEliminar) {
            eliminar();
            limpiarTabla();
            listar(vista.tabla);
        }

    }

    public void limpiarTabla() {
        for (int i = 0; i < vista.tabla.getRowCount(); i++) {
            modelo.removeRow(i);
            i = i - 1;
        }
    }

    public void eliminar() {
        int fila = vista.tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Debe seleccionar una fila");
        } else {
            int id = Integer.parseInt((String) vista.tabla.getValueAt(fila, 0).toString());
            ad_persona.Delete(id);
            JOptionPane.showMessageDialog(vista, "Usuario eliminado");

        }
    }

    public void actualizar() {
        //traemos el id tbm de persona
        int id = Integer.parseInt(vista.txtId.getText());
        String nombres = vista.txtNombre.getText();
        String correo = vista.txtCorreo.getText();
        int telefono = Integer.parseInt(vista.txtTelefono.getText());
        //pasamos los nuevos datos
        persona.setId(id);
        persona.setNombres(nombres);
        persona.setCorreo(correo);
        persona.setTelefono(telefono);

        boolean r = ad_persona.Actualizar(persona);

        if (r == true) {
            JOptionPane.showMessageDialog(vista, "Usuario actualizado con exito!!");
        } else {
            JOptionPane.showMessageDialog(vista, "Error!!.No se pudo actualizar al usuario");
        }
    }

    public void insertar() {
        //2.declaramos las variablesy lo obtenemos de la vista
        String nombres = vista.txtNombre.getText();
        String correo = vista.txtCorreo.getText();
        String telefono = vista.txtTelefono.getText();
        //3.los colocamos con sus parametrosy se parsea segun sea necesario
        persona.setNombres(nombres);
        persona.setCorreo(correo);
        persona.setTelefono(Integer.parseInt(telefono));
        //4. se coloca el objeto en el metodo insertar
        boolean r = ad_persona.Insertar(persona);

        if (r == true) {
            JOptionPane.showMessageDialog(vista, "Usuario agregado con exito!!");
        } else {
            JOptionPane.showMessageDialog(vista, "Error!!.No se pudo ingresar el usuario");
        }
    }

    public void listar(JTable tabla) {
        modelo = (DefaultTableModel) tabla.getModel();
        List<Persona> listado = ad_persona.getAll();
        Object[] object = new Object[4];//cantidad de columnas
        for (int i = 0; i < listado.size(); i++) {
            object[0] = listado.get(i).getId();
            object[1] = listado.get(i).getNombres();
            object[2] = listado.get(i).getCorreo();
            object[3] = listado.get(i).getTelefono();
            modelo.addRow(object);
        }
    }

}
