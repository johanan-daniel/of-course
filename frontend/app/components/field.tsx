import React from 'react'
import type { InputHTMLAttributes } from 'react'

interface FieldProps extends InputHTMLAttributes<HTMLInputElement> {
    className?: string
}

// check type & props passed
export default function field({ className = '', ...props }: FieldProps) {
    const combinedClassName = `field ${className}`

    return <input className={combinedClassName} {...props} />
}
