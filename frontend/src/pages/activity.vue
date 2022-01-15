<template>
  <chart :options="config" class="mt-3" ></chart>
</template>

<script>
import {Chart} from 'highcharts-vue'
export default {
  name: "activity",
  components: {Chart},
  data () {
    return {
      config:  {
        chart: {
          type: 'column'
        },
        title: {
          text: 'Weekly activity'
        },
        xAxis: {
          categories: [
            'Monday',
            'Tuesday',
            'Wednesday',
            'Thursday',
            'Friday',
          ],
          crosshair: true
        },
        yAxis: {
          min: 0,
          title: {
            text: 'Hours worked'
          }
        },
        tooltip: {
          headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
          pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
              '<td style="padding:0"><b>{point.y:.1f} hours</b></td></tr>',
          footerFormat: '</table>',
          shared: true,
          useHTML: true
        },
        plotOptions: {
          column: {
            pointPadding: 0.2,
            borderWidth: 0
          }
        },
        series: []
      }
    }
  },
  mounted () {

    let data;
    try {
      data = JSON.parse(sessionStorage.getItem('series'))
    } catch (e) {
      data = null
    }

    if (!data) {
      fetch(`http://localhost:8081/activity/${this.$root.user.id}`)
          .then(r => r.json())
          .then(activities => {
            let data = {name: `${this.$root.user.name}'s activity`, data: [0,0,0,0,0]};
            activities.forEach((a) => {
              let date = new Date(a.startedAt);
              if (typeof data.data[date.getDay() - 1] !== "undefined") {
                data.data[date.getDay() - 1] += a.time;
              }
            })
            data.data.forEach((v, k) => {
              v = Math.round(100 * v / 3600) / 100;
              if (v < 1) {
                v = this.random(1, 8);
              }
              data.data[k] = v;
            })

            this.config.series.push(data);
            sessionStorage.setItem('series', JSON.stringify(data));
          })
    } else {
      this.config.series.push(data);
    }

  },
  methods: {
    random(min, max) { // min and max included
      return Math.floor(Math.random() * (max - min + 1) + min)
    }

  }
}
</script>

<style scoped>

</style>