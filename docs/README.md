# KeyValue Contextcontributor

This module will load a webcontent item with a json field.
Then you can start using it in information templates and widget templates like:

```injectedfreemarker
<#assign myJson = KeyValueContributor.getJson("93197")?eval >
<div> ${myJson.city} </div>
```
