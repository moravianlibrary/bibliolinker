recordtype = "mzk"
allfields = getAllFields()
fullrecord = getFullrecord()
lccn = getFields "010a"
ctrlnum = getFields "035a"

mzk_visible_str = getMZKVisible()

format = translate("mzk_format.map", getMZKFormat(), null)
statuses = getMZKStatuses()

language = translate("mzk_language.map", getLanguages(), null)
language_display_mv = translate("mzk_language.map", getLanguages(), null)

country_txt = translate("mzk_country.map", getCountry(), null)
country_display_mv = translate("mzk_country.map", getCountry(), null)

author = getFirstField "100abcd"
author_fuller = getFirstField "100q"
author_letter = getFirstField "100a"
author2 = getFields "110ab:111ab:700abcd:710ab:711ab"
author2_role = getFields "700e:710e"
author_additional = getFields "505r"
author_display = getFirstField "100abcd"
author2_display_mv = getFields "110ab:111ab:700abcd:710ab:711ab"

title = getFirstField "245abnp"
title_sub = getFirstField "245b"
title_short = getFirstField "245a" // FIXME: getShortTitle()
title_full = getFirstField "245abdefghijklmnopqrstuvwxyz0123456789"
title_auth = getFirstField "245ab"
title_alt = getFields "130adfgklnpst:240a:246a:730adfgklnpst:740a"
title_old = getFields "780ast"
title_new = getFields "785ast"
title_sort = getSortableTitle()
title_display = getFirstField "245abnp"
title_sub_display = getFirstField "245b"

series = getFields "440ap:800abcdfpqt:830ap"
series2 = getFields "490a"

publisher = getPublisher()
publishDate_display = getPublishDateDisplay()
placeOfPublication_txt_mv = getFieldsTrim "260a:264a"
publishDate = getPublishDate()
publishDate_txt_mv = getPublishDate()
publishDateSort = getPublishDateForSorting()

physical = getFields "300abcefg:530abcd"
dateSpan = getFields "362a"
edition = getFirstField "250a"
contents = getFields "505a:505t"

isbn = getFields "020a"
isbn_display_mv = getFields "020a"
issn = getFields "022a:440x:490x:730x:776x:780x:785x"

callnumber_str_mv = getFields "910b"

topic = getFields "600:610:630:650"
genre = getFields "655"
geographic = getFields "651"

topic_facet = getFields "600x:610x:611x:630x:648x:650a:650x:651x:655x"
genre_facet = getFields "600v:610v:611v:630v:648v:650v:651v:655a:655v"
geographic_facet = getFields "600z:610z:611z:630z:648z:650z:651a:651z:655z"

url = getFields "856u"

illustrated = "FIXME"

bbox_geo = getBoundingBoxAsPolygon()
bbox_geo_str = getBoundingBox()

availability_id_str = getFirstField "996w"

fulltext = "" // custom, getFullText()
topic = getMZKKeywords()
relevancy_str = getMZKRelevancy()

// callnumber = getFields("910b")
callnumber_str_mv = getFields("910b")
callnumber_second_str_mv = getFields("996h").collect{it -> it.replace(' ', '|')}

// source = "MZK"
nbn = getFirstField "015a"
acq_int = getMZKAcquisitionDate()
category_txtF = translate("conspectus_category.map",
  getRecord().getDataFields("072").findAll{ df -> df?.getSubfield('2' as char)?.getData() == 'Konspekt' }
  .collect{ df -> df?.getSubfield('9' as char)?.getData() }.find{ true }, null);
subcategory_txtF = getRecord().getDataFields("072").findAll{ df -> df?.getSubfield('2' as char)?.getData() == 'Konspekt' }
  .collect{ df -> df?.getSubfield('x' as char)?.getData() }.find{ true };
base_txtF_mv = getMZKBases()
barcode_str_mv = getFields "996b"
sysno_str = "" // custom, getSysno()
publishDate = "" // custom, getPublishDate()
author_title_str = "" // custom, getAuthorAndTitle()
udc_str_mv = getFields "080a"
topic_facet = "" // custom, getTopicFacets()