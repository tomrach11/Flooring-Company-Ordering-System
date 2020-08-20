package com.tr.flooring;

import com.tr.flooring.controller.FlooringController;
import com.tr.flooring.dao.*;
import com.tr.flooring.service.FlooringServiceLayer;
import com.tr.flooring.service.FlooringServiceLayerFileImpl;
import com.tr.flooring.ui.FlooringView;
import com.tr.flooring.ui.UserIO;
import com.tr.flooring.ui.UserIOFileImpl;

public class App {

    public static void main(String[] args) throws FlooringFilePersistenceException {

        UserIO io = new UserIOFileImpl();
        FlooringView view = new FlooringView(io);

        FlooringOrderDao orderDao = new FlooringOrderMockDaoFileImpl("mockDataFile.txt");
//        FlooringOrderDao orderDao = new FlooringOrderDaoFileImpl();

        String PRODUCT_FILE = "products.txt";
        FlooringProductDao productDao = new FlooringProductDaoFileImpl(PRODUCT_FILE);

        String TAX_FILE = "tax.txt";
        FlooringTaxDao taxDao = new FlooringTaxDaoFileImpl(TAX_FILE);

        FlooringAuditDao audit = new FlooringAuditDaoFileImpl();

        FlooringServiceLayer service = new FlooringServiceLayerFileImpl(orderDao, productDao, taxDao, audit);

        FlooringController controller = new FlooringController(view, service);

        controller.run();

    }
}
