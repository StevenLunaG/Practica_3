package vista;

import controlador.RegistroControl;
import controlador.VentaControl;
import controlador.Venta.ViviendaArchivos;
import controlador.TDA.listas.DynamicList;
import controlador.TDA.listas.Exception.EmptyException;
import controlador.Utiles.Utiles;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

import modelo.Venta;
import modelo.Vivienda;
import vista.listas.tablas.VentaTabla;
import vista.listas.util.Utilvista;

public class FrmRegistroInmobiliario extends javax.swing.JFrame {
    
    private VentaControl ventaControl = new VentaControl();
    private VentaTabla vt = new VentaTabla();
    private controlador.Venta.VentaArchivos control = new controlador.Venta.VentaArchivos();
    
    private RegistroControl registroControl = new RegistroControl();
    private controlador.Venta.RegistroArchivos rControl = new controlador.Venta.RegistroArchivos();
    
    FrmNuevaVivienda nuevaVivienda = new FrmNuevaVivienda();
    
    private void cargarVista(int fila){
            try {
                ventaControl.setVenta(vt.getVentas().getInfo(fila));
                
                txtVenApellido.setText(ventaControl.getVenta().getVendedor().getApellido());
                txtVenDni.setText(ventaControl.getVenta().getVendedor().getDni());
                txtVenDni.setEnabled(false);
                txtVenNombre.setText(ventaControl.getVenta().getVendedor().getNombre());
                txtVenTelef.setText(ventaControl.getVenta().getVendedor().getTelefono());
                txtAgencia.setText(ventaControl.getVenta().getVendedor().getAgencia());
                txtPorcentaje.setText(ventaControl.getVenta().getVendedor().getPorcentajeComision().toString());
                
                txtClieApellido.setText(ventaControl.getVenta().getCliente().getApellido());
                txtClieDni.setText(ventaControl.getVenta().getCliente().getDni());
                txtClieDni.setEnabled(false);
                txtClieNombre.setText(ventaControl.getVenta().getCliente().getNombre());
                txtClieTelef.setText(ventaControl.getVenta().getCliente().getTelefono());

                txtFecha.setText(ventaControl.getVenta().getFecha().toString());
                txtFecha.setEnabled(false);
                txtSMonto.setText(ventaControl.getVenta().getMonto().toString());

                cbxCasa.setSelectedIndex(ventaControl.getVenta().getVivienda().getId());
                cbxCasa.setEnabled(false);
                
            } catch (Exception ex) {
                Logger.getLogger(FrmRegistroInmobiliario.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    public Boolean verificar() {
        return (!txtVenApellido.getText().trim().isEmpty()
                && !txtVenNombre.getText().trim().isEmpty()
                && !txtVenDni.getText().trim().isEmpty()
                && !txtClieApellido.getText().trim().isEmpty()
                && !txtClieNombre.getText().trim().isEmpty()
                && !txtClieDni.getText().trim().isEmpty()
                && !txtFecha.getText().trim().isEmpty()
                && !cbxCasa.getSelectedItem().toString().trim().isEmpty()
                && !txtSMonto.getText().trim().isEmpty());
    }
    
    private void cargarTabla() {
        vt.setVentas(control.all());
        tbVenta.setModel(vt);
        tbVenta.updateUI();
    }
    
    private void cargarCombo(){
        try {
                Utilvista.cargarComboViviendas(cbxCasa);
            } catch (EmptyException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
    }
    
    private void limpiar() {
        try {
            Utilvista.cargarComboViviendas(cbxCasa);
        } catch (EmptyException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        tbVenta.clearSelection();
        
        txtVenDni.setEnabled(true);
        txtVenApellido.setText("");
        txtVenDni.setText("");
        txtVenNombre.setText("");
        txtVenTelef.setText("");
        txtAgencia.setText("");
        txtPorcentaje.setText("");
        
        
        txtClieDni.setEnabled(true);
        txtClieApellido.setText("");
        txtClieDni.setText("");
        txtClieNombre.setText("");
        txtClieTelef.setText("");
        
        txtFecha.setEnabled(true);
        txtFecha.setText("yyyy-mm-dd");
        txtSMonto.setText("");
        cbxCasa.setEnabled(true);
        
        cargarTabla();
        ventaControl.setVenta(null);
        cbxCasa.setSelectedIndex(-1);
    }
    
    private void guardar() throws EmptyException {
        if (verificar()) {

            if (cbxCasa.getSelectedItem().toString().contains("[No Disponible]")) {
                JOptionPane.showMessageDialog(null, "La vivienda no esta disponible", "Error", JOptionPane.ERROR_MESSAGE);
            } else{

            if (Utiles.validadorDeCedula(txtVenDni.getText()) && Utiles.validadorDeCedula(txtClieDni.getText())) {

                //Datos Vendedor
                ventaControl.getVenta().getVendedor().setDni(txtVenDni.getText());
                ventaControl.getVenta().getVendedor().setApellido(txtVenApellido.getText());
                ventaControl.getVenta().getVendedor().setNombre(txtVenNombre.getText());
                ventaControl.getVenta().getVendedor().setTelefono(txtVenTelef.getText());
                ventaControl.getVenta().getVendedor().setAgencia(txtAgencia.getText());
                ventaControl.getVenta().getVendedor().setPorcentajeComision(Double.valueOf(txtPorcentaje.getText()));
                
                //Datos Cliente
                ventaControl.getVenta().getCliente().setDni(txtClieDni.getText());
                ventaControl.getVenta().getCliente().setApellido(txtClieApellido.getText());
                ventaControl.getVenta().getCliente().setNombre(txtClieNombre.getText());
                ventaControl.getVenta().getCliente().setTelefono(txtClieTelef.getText());

                ventaControl.getVenta().setFecha(Utiles.stringToDate(txtFecha.getText()));

                ventaControl.getVenta().setVivienda(cbxCasa.getSelectedIndex());
                
                ventaControl.getVenta().getVivienda().setDisponible(false);
                modificarDisponibilidadCasa(ventaControl.getVenta().getVivienda(), ventaControl.getVenta().getVivienda().getId());
                
                ventaControl.getVenta().setMonto(Double.valueOf(txtSMonto.getText()));
                if (ventaControl.guardar()) {
                    control.persist(ventaControl.getVenta());
                    JOptionPane.showMessageDialog(null, "Datos guardados");
                    cargarTabla();
                    limpiar();
                    ventaControl.setVenta(null);
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo guardar, hubo un error");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Cedula no valida");
            }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Falta llenar campos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void generarRegistro() throws EmptyException{
        registroControl.getRegistro().setVentaList(control.all());
        
        if (registroControl.guardar()) {
                    rControl.persist(registroControl.getRegistro());
                    JOptionPane.showMessageDialog(null, "Datos guardados");
                    cargarTabla();
                    limpiar();
                    ventaControl.setVenta(null);
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo guardar, hubo un error");
                }
    }
    
    private void modificarDisponibilidadCasa(Vivienda vivienda, Integer id){
        ViviendaArchivos vc = new ViviendaArchivos();
        vc.merge(vivienda, id);
    }
    
    private void modificar() throws EmptyException{
        
        //Datos Vendedor
        ventaControl.getVenta().getVendedor().setApellido(txtVenApellido.getText());
        ventaControl.getVenta().getVendedor().setNombre(txtVenNombre.getText());
        ventaControl.getVenta().getVendedor().setTelefono(txtVenTelef.getText());
        ventaControl.getVenta().getVendedor().setAgencia(txtAgencia.getText());
        ventaControl.getVenta().getVendedor().setPorcentajeComision(Double.valueOf(txtPorcentaje.getText()));

        //Datos Cliente
        ventaControl.getVenta().getCliente().setApellido(txtClieApellido.getText());
        ventaControl.getVenta().getCliente().setNombre(txtClieNombre.getText());
        ventaControl.getVenta().getCliente().setTelefono(txtClieTelef.getText());


        ventaControl.getVenta().setMonto(Double.valueOf(txtSMonto.getText()));
        control.merge(ventaControl.getVenta(), ventaControl.getVenta().getId());
        
        cargarTabla();
        limpiar();
    }
    

    /**
     * Creates new form FrmRegistroInmobiliario
     */
    public FrmRegistroInmobiliario() {
        initComponents();
        this.setLocationRelativeTo(null);
        limpiar();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtMonto = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        lblVivienda = new javax.swing.JLabel();
        cbxCasa = new javax.swing.JComboBox<>();
        btAniadirCasa = new javax.swing.JButton();
        lblVendedor = new javax.swing.JLabel();
        lblVenDni = new javax.swing.JLabel();
        txtVenDni = new javax.swing.JTextField();
        lblVenTelef = new javax.swing.JLabel();
        txtVenTelef = new javax.swing.JTextField();
        lblVenApellido = new javax.swing.JLabel();
        txtVenApellido = new javax.swing.JTextField();
        lblVenNombre = new javax.swing.JLabel();
        txtVenNombre = new javax.swing.JTextField();
        lblCliente = new javax.swing.JLabel();
        lblClieDni = new javax.swing.JLabel();
        txtClieDni = new javax.swing.JTextField();
        lblClieTelef = new javax.swing.JLabel();
        txtClieTelef = new javax.swing.JTextField();
        lblClieApellido = new javax.swing.JLabel();
        txtClieApellido = new javax.swing.JTextField();
        lblClieNombre = new javax.swing.JLabel();
        txtClieNombre = new javax.swing.JTextField();
        lblVenta = new javax.swing.JLabel();
        lblFecha = new javax.swing.JLabel();
        txtFecha = new javax.swing.JTextField();
        lblMonto = new javax.swing.JLabel();
        lblMoney = new javax.swing.JLabel();
        btGuardar1 = new javax.swing.JButton();
        btLimpiar1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbVenta = new javax.swing.JTable();
        btSeleccionar1 = new javax.swing.JButton();
        btModificar = new javax.swing.JButton();
        txtSMonto = new javax.swing.JTextField();
        lblVenApellido1 = new javax.swing.JLabel();
        txtAgencia = new javax.swing.JTextField();
        lblComision = new javax.swing.JLabel();
        txtPorcentaje = new javax.swing.JTextField();
        lblMoney1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTitulo.setText("Registrar Venta");

        lblVivienda.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblVivienda.setText("Vivienda");

        cbxCasa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxCasa.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
                cbxCasaPopupMenuWillBecomeVisible(evt);
            }
        });
        cbxCasa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxCasaActionPerformed(evt);
            }
        });

        btAniadirCasa.setText("Añadir Vivienda");
        btAniadirCasa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAniadirCasaActionPerformed(evt);
            }
        });

        lblVendedor.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblVendedor.setText("Vendedor");

        lblVenDni.setText("Dni:");

        lblVenTelef.setText("Telefono:");

        lblVenApellido.setText("Apellido:");

        txtVenApellido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtVenApellidoActionPerformed(evt);
            }
        });

        lblVenNombre.setText("Nombre:");

        lblCliente.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblCliente.setText("Cliente");

        lblClieDni.setText("Dni:");

        lblClieTelef.setText("Telefono:");

        lblClieApellido.setText("Apellido:");

        txtClieApellido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtClieApellidoActionPerformed(evt);
            }
        });

        lblClieNombre.setText("Nombre:");

        lblVenta.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblVenta.setText("Venta");

        lblFecha.setText("Fecha:");

        txtFecha.setForeground(new java.awt.Color(0, 0, 0));

        lblMonto.setText("Monto:");

        lblMoney.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblMoney.setText("$");

        btGuardar1.setText("Guardar");
        btGuardar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btGuardar1ActionPerformed(evt);
            }
        });

        btLimpiar1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btLimpiar1.setText("Limpiar/Actualizar");
        btLimpiar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btLimpiar1ActionPerformed(evt);
            }
        });

        tbVenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbVenta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbVentaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbVenta);

        btSeleccionar1.setText("Cargar");
        btSeleccionar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSeleccionar1ActionPerformed(evt);
            }
        });

        btModificar.setText("Modificar");
        btModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btModificarActionPerformed(evt);
            }
        });

        txtSMonto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSMontoActionPerformed(evt);
            }
        });

        lblVenApellido1.setText("Agencia:");

        txtAgencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAgenciaActionPerformed(evt);
            }
        });

        lblComision.setText("Comision:");

        txtPorcentaje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPorcentajeActionPerformed(evt);
            }
        });

        lblMoney1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblMoney1.setText("%");

        javax.swing.GroupLayout txtMontoLayout = new javax.swing.GroupLayout(txtMonto);
        txtMonto.setLayout(txtMontoLayout);
        txtMontoLayout.setHorizontalGroup(
            txtMontoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(txtMontoLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(txtMontoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(txtMontoLayout.createSequentialGroup()
                        .addGroup(txtMontoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(txtMontoLayout.createSequentialGroup()
                                .addGroup(txtMontoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblClieDni)
                                    .addComponent(lblClieApellido)
                                    .addComponent(lblFecha))
                                .addGap(18, 18, 18)
                                .addGroup(txtMontoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtClieApellido)
                                    .addComponent(txtClieDni)
                                    .addComponent(txtFecha))
                                .addGap(18, 18, 18)
                                .addGroup(txtMontoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblClieNombre)
                                    .addComponent(lblClieTelef)
                                    .addComponent(lblMonto))
                                .addGap(18, 18, 18)
                                .addGroup(txtMontoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtClieNombre)
                                    .addComponent(txtClieTelef, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(txtMontoLayout.createSequentialGroup()
                                        .addComponent(lblMoney)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtSMonto, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, txtMontoLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(lblVenApellido1)
                                .addGap(18, 18, 18)
                                .addComponent(txtAgencia, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblComision)
                                .addGap(17, 17, 17)
                                .addComponent(txtPorcentaje, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblMoney1)
                                .addGap(58, 58, 58)))
                        .addGap(60, 60, 60))
                    .addComponent(lblVenta)
                    .addComponent(lblCliente)
                    .addComponent(lblVendedor)
                    .addComponent(lblVivienda)
                    .addGroup(txtMontoLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(cbxCasa, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(btAniadirCasa))
                    .addGroup(txtMontoLayout.createSequentialGroup()
                        .addGroup(txtMontoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblVenDni)
                            .addComponent(lblVenApellido))
                        .addGap(18, 18, 18)
                        .addGroup(txtMontoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtVenApellido)
                            .addComponent(txtVenDni, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(txtMontoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblVenNombre)
                            .addComponent(lblVenTelef))
                        .addGap(18, 18, 18)
                        .addGroup(txtMontoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtVenNombre)
                            .addComponent(txtVenTelef, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(txtMontoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(txtMontoLayout.createSequentialGroup()
                        .addGap(102, 102, 102)
                        .addComponent(btSeleccionar1)
                        .addGap(41, 41, 41)
                        .addComponent(btModificar)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, txtMontoLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44))))
            .addGroup(txtMontoLayout.createSequentialGroup()
                .addGroup(txtMontoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(txtMontoLayout.createSequentialGroup()
                        .addGap(437, 437, 437)
                        .addComponent(lblTitulo))
                    .addGroup(txtMontoLayout.createSequentialGroup()
                        .addGap(129, 129, 129)
                        .addComponent(btGuardar1, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(66, 66, 66)
                        .addComponent(btLimpiar1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        txtMontoLayout.setVerticalGroup(
            txtMontoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(txtMontoLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(lblTitulo)
                .addGap(18, 18, 18)
                .addComponent(lblVivienda)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(txtMontoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btAniadirCasa)
                    .addGroup(txtMontoLayout.createSequentialGroup()
                        .addComponent(cbxCasa, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)))
                .addGap(22, 22, 22)
                .addComponent(lblVendedor)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(txtMontoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(txtMontoLayout.createSequentialGroup()
                        .addGroup(txtMontoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtVenDni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblVenDni)
                            .addComponent(txtVenTelef, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblVenTelef))
                        .addGap(18, 18, 18)
                        .addGroup(txtMontoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtVenApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblVenApellido)
                            .addComponent(lblVenNombre)
                            .addComponent(txtVenNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(txtMontoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtAgencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblVenApellido1)
                            .addComponent(lblComision)
                            .addComponent(txtPorcentaje, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblMoney1))
                        .addGap(35, 35, 35)
                        .addComponent(lblCliente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(txtMontoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtClieDni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblClieDni)
                            .addComponent(txtClieTelef, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblClieTelef))
                        .addGap(18, 18, 18)
                        .addGroup(txtMontoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtClieApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblClieApellido)
                            .addComponent(lblClieNombre)
                            .addComponent(txtClieNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(41, 41, 41)
                        .addComponent(lblVenta)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(txtMontoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblFecha)
                            .addComponent(lblMoney)
                            .addComponent(lblMonto)
                            .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSMonto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(44, 44, 44)
                        .addGroup(txtMontoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btGuardar1)
                            .addComponent(btLimpiar1)))
                    .addGroup(txtMontoLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addGroup(txtMontoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btSeleccionar1)
                            .addComponent(btModificar))))
                .addContainerGap(41, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtMonto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtMonto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbxCasaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxCasaActionPerformed

    }//GEN-LAST:event_cbxCasaActionPerformed

    private void txtVenApellidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtVenApellidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtVenApellidoActionPerformed

    private void txtClieApellidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtClieApellidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtClieApellidoActionPerformed

    private void btAniadirCasaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAniadirCasaActionPerformed
        nuevaVivienda.setVisible(true);
    }//GEN-LAST:event_btAniadirCasaActionPerformed

    private void cbxCasaPopupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbxCasaPopupMenuWillBecomeVisible
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxCasaPopupMenuWillBecomeVisible

    private void btGuardar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btGuardar1ActionPerformed
        try {
            generarRegistro();
        } catch (EmptyException ex) {
            System.out.println(ex.getMessage());
        }
    }//GEN-LAST:event_btGuardar1ActionPerformed

    private void btLimpiar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btLimpiar1ActionPerformed
        limpiar();
    }//GEN-LAST:event_btLimpiar1ActionPerformed

    private void btSeleccionar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSeleccionar1ActionPerformed
        //cargarVista();
    }//GEN-LAST:event_btSeleccionar1ActionPerformed

    private void btModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btModificarActionPerformed
        try {
            modificar();
        } catch (EmptyException ex) {
            Logger.getLogger(FrmRegistroInmobiliario.class.getName()).log(Level.SEVERE, null, ex);
        }
        JOptionPane.showMessageDialog(null, "Datos Modificados");
    }//GEN-LAST:event_btModificarActionPerformed

    private void txtAgenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAgenciaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAgenciaActionPerformed

    private void txtSMontoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSMontoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSMontoActionPerformed

    private void txtPorcentajeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPorcentajeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPorcentajeActionPerformed

    private void tbVentaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbVentaMouseClicked
        int fila = tbVenta.getSelectedRow();
        cargarVista(fila);
    }//GEN-LAST:event_tbVentaMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmRegistroInmobiliario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmRegistroInmobiliario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmRegistroInmobiliario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmRegistroInmobiliario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmRegistroInmobiliario().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAniadirCasa;
    private javax.swing.JButton btGuardar1;
    private javax.swing.JButton btLimpiar1;
    private javax.swing.JButton btModificar;
    private javax.swing.JButton btSeleccionar1;
    private javax.swing.JComboBox<String> cbxCasa;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblClieApellido;
    private javax.swing.JLabel lblClieDni;
    private javax.swing.JLabel lblClieNombre;
    private javax.swing.JLabel lblClieTelef;
    private javax.swing.JLabel lblCliente;
    private javax.swing.JLabel lblComision;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblMoney;
    private javax.swing.JLabel lblMoney1;
    private javax.swing.JLabel lblMonto;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblVenApellido;
    private javax.swing.JLabel lblVenApellido1;
    private javax.swing.JLabel lblVenDni;
    private javax.swing.JLabel lblVenNombre;
    private javax.swing.JLabel lblVenTelef;
    private javax.swing.JLabel lblVendedor;
    private javax.swing.JLabel lblVenta;
    private javax.swing.JLabel lblVivienda;
    private javax.swing.JTable tbVenta;
    private javax.swing.JTextField txtAgencia;
    private javax.swing.JTextField txtClieApellido;
    private javax.swing.JTextField txtClieDni;
    private javax.swing.JTextField txtClieNombre;
    private javax.swing.JTextField txtClieTelef;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JPanel txtMonto;
    private javax.swing.JTextField txtPorcentaje;
    private javax.swing.JTextField txtSMonto;
    private javax.swing.JTextField txtVenApellido;
    private javax.swing.JTextField txtVenDni;
    private javax.swing.JTextField txtVenNombre;
    private javax.swing.JTextField txtVenTelef;
    // End of variables declaration//GEN-END:variables
}
