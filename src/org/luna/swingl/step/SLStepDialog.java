/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.luna.swingl.step;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author tangzhichao
 */
public class SLStepDialog extends javax.swing.JDialog {

    /**
     * Creates new form AbstractStep
     */
    public SLStepDialog() {
        super((JFrame) null, true);
        initComponents();
        this.stepList.setModel(new StepListModel());
        this.stepList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.stepList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) {
                    return;
                }
                int row = stepList.getSelectedIndex();
                if (row == -1) {
                    return;
                }
                StepListModel model = (StepListModel) stepList.getModel();
                JComponent comp = (JComponent) model.getElementAt(row);
                mainPane.removeAll();
                mainPane.add(comp);
                mainPane.updateUI();
            }
        });
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (stepList.getModel().getSize() > 0) {
                    stepList.addSelectionInterval(0, 0);
                }
            }
        });
        this.previousBtn.setAction(new PreviousAction());
        this.nextBtn.setAction(new NextAction());
        this.finishBtn.setAction(new FinishAction());
    }

    public void addItem(StepItemPane item) {
        StepListModel model = (StepListModel) this.stepList.getModel();
        model.addItem(item);
    }

    abstract class StepAction extends AbstractAction {

        public StepAction() {
        }

        public abstract void actionPerformed(ActionEvent e);

        public abstract boolean isEnabled();

        public void fireEnabledPropertyChanged() {
            super.firePropertyChange("enabled", true, false);
        }
    }

    class PreviousAction extends StepAction {

        public PreviousAction() {
            this.putValue(NAME, "上一步");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int row = stepList.getSelectedIndex();
            stepList.clearSelection();
            stepList.setSelectedIndex(row - 1);
        }

        @Override
        public boolean isEnabled() {
            return stepList.getSelectedIndex() >= 1;
        }

    }

    class NextAction extends StepAction {

        public NextAction() {
            putValue(NAME, "下一步");
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            int row = stepList.getSelectedIndex();
            stepList.clearSelection();
            stepList.setSelectedIndex(row + 1);
        }

        @Override
        public boolean isEnabled() {
            return stepList.getSelectedIndex() < stepList.getModel().getSize() - 1;
        }

    }
    
    class FinishAction extends StepAction {

        public FinishAction() {
            putValue(NAME, "完成");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            SLStepDialog.this.setVisible(false);
        }

        @Override
        public boolean isEnabled() {
           return  stepList.getSelectedIndex() == stepList.getModel().getSize() -1;
        }
        
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        previousBtn = new javax.swing.JButton();
        nextBtn = new javax.swing.JButton();
        finishBtn = new javax.swing.JButton();
        cancelBtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        stepList = new javax.swing.JList<>();
        mainPane = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        previousBtn.setText("jButton1");
        jPanel1.add(previousBtn);

        nextBtn.setText("jButton2");
        jPanel1.add(nextBtn);

        finishBtn.setText("jButton3");
        jPanel1.add(finishBtn);

        cancelBtn.setText("jButton4");
        jPanel1.add(cancelBtn);

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_END);

        jScrollPane1.setMinimumSize(new java.awt.Dimension(250, 250));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(250, 163));

        stepList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(stepList);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.LINE_START);

        mainPane.setLayout(new java.awt.BorderLayout());
        getContentPane().add(mainPane, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelBtn;
    private javax.swing.JButton finishBtn;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel mainPane;
    private javax.swing.JButton nextBtn;
    private javax.swing.JButton previousBtn;
    private javax.swing.JList<String> stepList;
    // End of variables declaration//GEN-END:variables
}