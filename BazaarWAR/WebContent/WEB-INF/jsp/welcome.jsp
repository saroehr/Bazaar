<?xml version="1.0" encoding="ISO-8859-1" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0">
	<jsp:directive.page contentType="text/html; charset=ISO-8859-1"
		pageEncoding="ISO-8859-1" session="false" />
	<jsp:directive.page import="org.apache.bazaar.web.i18n.Messages" />
	<jsp:directive.page import="java.util.Locale" />
	<jsp:output doctype-root-element="html"
		doctype-public="-//W3C//DTD XHTML 1.0 Transitional//EN"
		doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"
		omit-xml-declaration="true" />
	<html xmlns="http://www.w3.org/1999/xhtml">
		<head>
			<jsp:declaration>final Messages messages = Messages.newInstance(Locale.getDefault());</jsp:declaration>
			<title><jsp:expression>messages.findMessage(Messages.WELCOME_JSP_TITLE)</jsp:expression></title>
		</head>
		<body>
			<h2><jsp:expression>messages.findMessage(Messages.WELCOME_JSP_HEADER)</jsp:expression></h2>	
		</body>
	</html>
</jsp:root>