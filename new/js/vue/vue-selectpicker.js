
// select 插件
// livesearch: 搜尋下拉選單 (true)
// multiple: 多選 (multiple), 多選時會有 "全選", "全不選" 兩個選項
// blank: 預設請選擇 (true)
Vue.component('v-select', {
    props: ['options', 'value', 'livesearch', 'multiple', 'blank'],
    template: `<select :multiple='multiple' :data-live-search='livesearch'>
                   <option :value='option.Code' v-for='option in options' :selected="option.Code===value">{{ option.Description }}</option>
               </select>`,
    mounted: function () {
        var vm = this;
        //alert(vm.livesearch);
        //alert(vm.multiple);
        //alert(this.value);
        //$(this.$el).val(this.value != null ? this.value : null);

        // 請選擇選項
        if (vm.blank) {
            $(this.$el).prepend('<option value="">請選擇</option>');
        }

        // 顯示文字(中文編碼)
        $(this.$el).selectpicker({
            noneSelectedText: '沒有選取任何項目',
            noneResultsText: '沒有找到符合的結果',
            countSelectedText: '已經選取{0}個項目',
            maxOptionsText: ['超過限制 (最多選擇{n}項)', '超過限制(最多選擇{n}組)'],
            selectAllText: '全選',
            deselectAllText: '全取消',
            multipleSeparator: ', ',
            size: 10,
            title: '--請選擇--',
            class: 'selectpicker',
            actionsBox: true // 顯示全選, 全取消
        });

        $(this.$el).selectpicker('val', this.value != null ? this.value : null);
        $(this.$el).val(this.value).selectpicker().on('changed.bs.select', function () {
            vm.$emit('input', $(this).selectpicker("val"));
        });

    },
    updated: function () {
        $(this.$el).selectpicker('refresh');
        $(this.$el).selectpicker("val", this.value);
    },
    destroyed: function () {
        $(this.$el).off().selectpicker('destroy');
    }
});
