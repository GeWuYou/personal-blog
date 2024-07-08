<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>
import { generaMenu } from '@/assets/js/menu'
import { _post } from '@/api/api'

export default {
  created() {
    if (this.$store.state.userInfo != null) {
      generaMenu()
    }
    _post('/admin/report', {}, () => {
    })
    // this.axios.post('/api/report')
  }
}
</script>
