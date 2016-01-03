(function($){

    /**
     * jQuery wrapper for form2object()
     * Extracts data from child inputs into javascript object
     */
     $.fn.toObject = function(options)
     {
        var result = [],
        settings = {
                mode: 'first', // what to convert: 'all' or 'first' matched node
                delimiter: ".",
                skipEmpty: true,
                nodeCallback: null,
                useIdIfEmptyName: false
            };

            if (options)
            {
                $.extend(settings, options);
            }

            switch(settings.mode)
            {
                case 'first':
                return form2js(this.get(0), settings.delimiter, settings.skipEmpty, settings.nodeCallback, settings.useIdIfEmptyName);
                break;
                case 'all':
                this.each(function(){
                    result.push(form2js(this, settings.delimiter, settings.skipEmpty, settings.nodeCallback, settings.useIdIfEmptyName));
                });
                return result;
                break;
                case 'combine':
                return form2js(Array.prototype.slice.call(this), settings.delimiter, settings.skipEmpty, settings.nodeCallback, settings.useIdIfEmptyName);
                break;
            }
        }
})(jQuery);

function post(url ,data, success){
            $.ajax({
              url: url,
              type: "POST",
              data:$.toJSON(data),
              processData: false,
              contentType: "application/json; charset=UTF-8"
          })
            .done(function(data){
                success(data)
            })
            .fail(function(jqXHR, textStatus, errorThrown ){
                alert(jqXHR.responseText);
            })
}

function getParameterByName(name) {
    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
        results = regex.exec(location.search);
    return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}