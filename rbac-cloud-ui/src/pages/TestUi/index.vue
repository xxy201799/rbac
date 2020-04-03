<template>
  <div class="TestUi-page">
    <!-- 固定列和表头的表格 -->
    <FixedTable />
  </div>
</template>
<script>
import FixedTable from './components/FixedTable';
export default {
  name: 'TestUi',
  components: {
    FixedTable,
  },
};
</script>
