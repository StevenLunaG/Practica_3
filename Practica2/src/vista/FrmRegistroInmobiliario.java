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
    private controlador.Venta.VentaArchivos fileVenta = new controlador.Venta.VentaArchivos();

    private RegistroControl registroControl = new RegistroControl();
    private controlador.Venta.RegistroArchivos rControl = new controlador.Venta.RegistroArchivos();

    FrmNuevaVivienda nuevaVivienda = new FrmNuevaVivienda();

    private void cargarVista(int fila) {
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
            btGuardarCambios.setEnabled(true);

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
        vt.setVentas(fileVenta.all());
        tbVenta.setModel(vt);
        tbVenta.updateUI();
    }

    private void cargarCombo() {
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
        btGuardarCambios.setEnabled(false);

        cargarTabla();
        ventaControl.setVenta(null);
        cbxCasa.setSelectedIndex(-1);
    }

    private void guardar() throws EmptyException {
        if (verificar()) {

            if (cbxCasa.getSelectedItem().toString().contains("[No Disponible]")) {
                JOptionPane.showMessageDialog(null, "La vivienda no esta disponible", "Error", JOptionPane.ERROR_MESSAGE);
            } else {

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
                        fileVenta.setVenta(ventaControl.getVenta());
                        fileVenta.persist();
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

//    private void generarRegistro() throws EmptyException{
//        registroControl.getRegistro().setVentaList(control.all());
//        
//        if (registroControl.guardar()) {
//                    rControl.persist(registroControl.getRegistro());
//                    JOptionPane.showMessageDialog(null, "Datos guardados");
//                    cargarTabla();
//                    limpiar();
//                    ventaControl.setVenta(null);
//                } else {
//                    JOptionPane.showMessageDialog(null, "No se pudo guardar, hubo un error");
//                }
//    }
    private void modificarDisponibilidadCasa(Vivienda vivienda, Integer id) {
        ViviendaArchivos vc = new ViviendaArchivos();
        vc.merge(vivienda, id);
    }

    private void modificar() throws EmptyException {

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
        fileVenta.merge(ventaControl.getVenta(), ventaControl.getVenta().getId());

        cargarTabla();
        limpiar();
    }

    private void ordenarShell() {

    }

    private void ordenar(Integer var) {
        String criterio = cbxCriterio.getSelectedItem().toString();
        Integer tipo = 0;
        switch (var) {
            case 1:
                if (cbxOrden.getSelectedIndex() == 1) {
                    tipo = 1;
                }
                try {
                    vt.setVentas(fileVenta.ordenarQuick(fileVenta.all(), criterio, tipo));
                    tbVenta.setModel(vt);
                    tbVenta.updateUI();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                }
                break;
            case 2:
                if (cbxOrden.getSelectedIndex() == 1) {
                    tipo = 1;
                }
                try {
                    vt.setVentas(fileVenta.ordenarShell(fileVenta.all(), criterio, tipo));
                    tbVenta.setModel(vt);
                    tbVenta.updateUI();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                }
                break;
            default:
                throw new AssertionError();
        }
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
        txtSMonto = new javax.swing.JTextField();
        lblVenApellido1 = new javax.swing.JLabel();
        txtAgencia = new javax.swing.JTextField();
        lblComision = new javax.swing.JLabel();
        txtPorcentaje = new javax.swing.JTextField();
        lblMoney1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btGuardarCambios = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbVenta = new javax.swing.JTable();
        lblVivienda1 = new javax.swing.JLabel();
        lblVenDni1 = new javax.swing.JLabel();
        cbxMetodo = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        cbxOrden = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        cbxCriterio = new javax.swing.JComboBox<>();
        btOrdenar = new javax.swing.JButton();

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
        cbxCasa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cbxCasaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cbxCasaMouseEntered(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                cbxCasaMousePressed(evt);
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

        btLimpiar1.setText("Limpiar");
        btLimpiar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btLimpiar1ActionPerformed(evt);
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

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btGuardarCambios.setText("Guardar Cambios");
        btGuardarCambios.setEnabled(false);
        btGuardarCambios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btGuardarCambiosActionPerformed(evt);
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

        lblVivienda1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblVivienda1.setText("Ordenar Registro");

        lblVenDni1.setText("Metodo de Ordenamiento:");

        cbxMetodo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Quicksort", "Shellsort" }));

        jLabel1.setText("Orden:");

        cbxOrden.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Descendente", "Ascendente" }));

        jLabel2.setText("Criterio:");

        cbxCriterio.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Registro", "Fecha", "Vivienda", "Vendedor", "Cliente" }));

        btOrdenar.setText("Ordenar");
        btOrdenar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btOrdenarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(148, 148, 148)
                        .addComponent(btGuardarCambios))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblVivienda1)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(lblVenDni1)
                                        .addGap(18, 18, 18)
                                        .addComponent(cbxMetodo, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(btOrdenar)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel1)
                                                .addGap(18, 18, 18)
                                                .addComponent(cbxOrden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(30, 30, 30)
                                                .addComponent(jLabel2)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(cbxCriterio, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblVivienda1)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblVenDni1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxMetodo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbxOrden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbxCriterio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(btOrdenar)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btGuardarCambios)
                .addGap(14, 14, 14))
        );

        javax.swing.GroupLayout txtMontoLayout = new javax.swing.GroupLayout(txtMonto);
        txtMonto.setLayout(txtMontoLayout);
        txtMontoLayout.setHorizontalGroup(
            txtMontoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(txtMontoLayout.createSequentialGroup()
                .addGap(437, 437, 437)
                .addComponent(lblTitulo)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(txtMontoLayout.createSequentialGroup()
                .addGap(28, 28, 28)
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
                    .addComponent(lblVenta)
                    .addComponent(lblCliente)
                    .addComponent(lblVivienda)
                    .addComponent(lblVendedor)
                    .addGroup(txtMontoLayout.createSequentialGroup()
                        .addGap(101, 101, 101)
                        .addComponent(btGuardar1, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(77, 77, 77)
                        .addComponent(btLimpiar1))
                    .addGroup(txtMontoLayout.createSequentialGroup()
                        .addComponent(lblVenApellido1)
                        .addGap(18, 18, 18)
                        .addComponent(txtAgencia, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblComision)
                        .addGap(18, 18, 18)
                        .addComponent(txtPorcentaje, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblMoney1))
                    .addGroup(txtMontoLayout.createSequentialGroup()
                        .addGroup(txtMontoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
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
                                    .addComponent(lblVenTelef)))
                            .addGroup(txtMontoLayout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(cbxCasa, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)
                        .addGroup(txtMontoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btAniadirCasa)
                            .addComponent(txtVenNombre)
                            .addComponent(txtVenTelef, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(62, 62, 62))
        );
        txtMontoLayout.setVerticalGroup(
            txtMontoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(txtMontoLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(lblTitulo)
                .addGap(18, 18, 18)
                .addGroup(txtMontoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(txtMontoLayout.createSequentialGroup()
                        .addComponent(lblVivienda)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(txtMontoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbxCasa, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btAniadirCasa))
                        .addGap(22, 22, 22)
                        .addComponent(lblVendedor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(txtMontoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtAgencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblVenApellido1)
                            .addComponent(txtPorcentaje, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblMoney1)
                            .addComponent(lblComision))
                        .addGap(21, 21, 21)
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
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtMonto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtMonto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
            guardar();
        } catch (EmptyException ex) {
            System.out.println(ex.getMessage());
        }
    }//GEN-LAST:event_btGuardar1ActionPerformed

    private void btLimpiar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btLimpiar1ActionPerformed
        limpiar();
    }//GEN-LAST:event_btLimpiar1ActionPerformed

    private void btGuardarCambiosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btGuardarCambiosActionPerformed
        try {
            modificar();
        } catch (EmptyException ex) {
            Logger.getLogger(FrmRegistroInmobiliario.class.getName()).log(Level.SEVERE, null, ex);
        }
        JOptionPane.showMessageDialog(null, "Datos Modificados");
    }//GEN-LAST:event_btGuardarCambiosActionPerformed

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

    private void cbxCasaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbxCasaMouseEntered

    }//GEN-LAST:event_cbxCasaMouseEntered

    private void btOrdenarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btOrdenarActionPerformed
        if (cbxMetodo.getSelectedIndex() == 0) {
            ordenar(1);
        } else if (cbxMetodo.getSelectedIndex() == 1) {
            ordenar(2);
        }
    }//GEN-LAST:event_btOrdenarActionPerformed

    private void cbxCasaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbxCasaMousePressed
        
    }//GEN-LAST:event_cbxCasaMousePressed

    private void cbxCasaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbxCasaMouseClicked
        if(cbxCasa.isEnabled()){
        try {
            Utilvista.cargarComboViviendas(cbxCasa);
        } catch (EmptyException ex) {
            Logger.getLogger(FrmRegistroInmobiliario.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }//GEN-LAST:event_cbxCasaMouseClicked

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
    private javax.swing.JButton btGuardarCambios;
    private javax.swing.JButton btLimpiar1;
    private javax.swing.JButton btOrdenar;
    private javax.swing.JComboBox<String> cbxCasa;
    private javax.swing.JComboBox<String> cbxCriterio;
    private javax.swing.JComboBox<String> cbxMetodo;
    private javax.swing.JComboBox<String> cbxOrden;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
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
    private javax.swing.JLabel lblVenDni1;
    private javax.swing.JLabel lblVenNombre;
    private javax.swing.JLabel lblVenTelef;
    private javax.swing.JLabel lblVendedor;
    private javax.swing.JLabel lblVenta;
    private javax.swing.JLabel lblVivienda;
    private javax.swing.JLabel lblVivienda1;
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
