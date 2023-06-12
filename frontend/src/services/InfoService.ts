import { reactive, readonly } from 'vue'

const x = { infonachricht: '' }
const state = reactive(x) // ermöglicht, Daten zu verwalten und automatisch die Darstellung zu aktualisieren, wenn sich diese Daten ändern

function setInfo(msg: string): void {
  state.infonachricht = msg
}

export function useInfo() {
  return {
    readonlyInfonachricht: readonly(state), // damit man nur über diese  Variable zugriff hat
    setInfo
  }
}
