table = new javax.swing.JTable(model){
            public Component prepareRenderer(TableCellRenderer renderer, int Index_row, int Index_col) {
                Component comp = super.prepareRenderer(renderer, Index_row, Index_col);
                //even index, selected or not selected
                if (Index_col == 0/* && !isCellSelected(Index_row, Index_col)*/) {
                    comp.setBackground(Color.gray);
                    comp.setForeground(Color.white);
                } else {
                    comp.setBackground(Color.white);
                    comp.setForeground(Color.GRAY);
                }

                comp.enableInputMethods(false);

                return comp;
            }
        };