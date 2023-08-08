package com.lawencon.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * @author lawencon05
 */
@Component
public class JasperUtil {

	@Value("${jasper.template-folder}")
	private String jasperTemplateFolder;
	
	public byte[] responseToByteArray(
			Collection<?> data, 
			Map<String, Object> mapParams,
			String jasperName
	) throws Exception {
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream(jasperTemplateFolder + "/" + jasperName + ".jasper");
			JasperReport jasper = (JasperReport) JRLoader.loadObject(inputStream);
			JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(data);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, mapParams, ds);
			JasperExportManager.exportReportToPdfStream(jasperPrint, out);
			return out.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}
}
