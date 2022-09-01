package com.liferay.demo.contextcontributor.key.value;

import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.template.TemplateContextContributor;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.xml.*;
import org.osgi.service.component.annotations.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


/**
 * @author jverweij
 */
@Component(
	immediate = true,
	property = {"type=" + TemplateContextContributor.TYPE_GLOBAL},
	service = TemplateContextContributor.class
)
public class KeyValueContextContributor
	implements TemplateContextContributor {

	ThemeDisplay themeDisplay;

	@Override
	public void prepare(
		Map<String, Object> contextObjects, HttpServletRequest request) {
		themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		contextObjects.put("KeyValueContributor", this);
	}

	public String getJson(String articleID) {

		String json = "{}";
		try {
			JournalArticle article = JournalArticleLocalServiceUtil.getArticle(themeDisplay.getSiteGroupId(),articleID);

			Document doc = SAXReaderUtil.read(article.getContent());
			Element root = doc.getRootElement();
			XPath xpath = SAXReaderUtil.createXPath("dynamic-element[@field-reference='configuration']");
			json = xpath.selectSingleNode(root).getStringValue();

		} catch (PortalException | DocumentException e) {
			throw new RuntimeException(e);
		}

		return json ;
	}

	private static final Log _log = LogFactoryUtil.getLog(KeyValueContextContributor.class);
}